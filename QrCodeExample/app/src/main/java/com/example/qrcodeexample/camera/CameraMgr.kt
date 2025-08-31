package com.example.qrcodeexample.camera

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionSelector.PREFER_CAPTURE_RATE_OVER_HIGHER_RESOLUTION
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.doOnAttach
import com.example.qrcodeexample.qrcode.analyze.QrCodeAnalyzer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * day3
 * 1、认识相机启动流程、预览流、分析流
 * 2、在首页加一个拍照按钮，给相机流程加上拍照流
 * 3、点击按钮响应拍照，并将拍照结果存储到应用文件（需要申请文件权限）
 * 4、拉起新页面，在 AppcompatImageView 展示存储到文件的图片
 */
class CameraMgr(
    private val context: Context,
    private val viewFinder: PreviewView
) {
    companion object {
        const val TAG: String = "CameraMgr"
        const val RATIO_4_3_VALUE = 4.0 / 3.0
        const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null

    private var imageAnalysis: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private var mAnalyzer: QrCodeAnalyzer = QrCodeAnalyzer().apply {
        resultListener = AnalyzerResultListener()
    }
    private val cameraAnalyzer = ImageAnalysis.Analyzer { image ->
        try {
            image.use {
                mAnalyzer.analyze(it)
            }
        } catch (e: Throwable) {
            e.stackTraceToString()
        }
    }

    private val captureExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val analyzerExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }

    // 回调接口
    interface CreateNewWebListener {
        fun createNewWeb(url: String?)
    }

    // 监听者(在主窗口那边的内部类里面实现)
    var createNewWebListener: CreateNewWebListener? = null

    // 监听analyzer的result回调
    // 这是第一级的回调，随后我们可以去实现一个二级回调
    // 二级回调里，扫一扫窗口那边会打开一个新的web窗口
    // 原本是想在这里销毁相机（这样做会带来一些异步问题，是非法的），扫一扫窗口那边打开网页
    // 后面发现扫一扫窗口的销毁函数调用了相机的销毁函数，所以只需要保持现在的用pause做个伪销毁就行了
    // 但是从层级上来说：扫一扫窗口-->Manager-->analyzer，所以二级回调还是合理的
    private inner class AnalyzerResultListener : QrCodeAnalyzer.ResultListener {
        override fun onResultGet(result: String?) {
            // destroyCamera()
            createNewWebListener?.createNewWeb(result)
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    fun startCamera() {
        // 这里之所以加上future，是因为拿到相机是一个异步的过程
        // 实际的返回值是ListenableFuture<ProcessCameraProvider>
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        // 注册监听器
        // 监听器的触发条件是：future拿到真正的对象
        // 监听器的函数体在此之后才被执行
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        cameraProvider.unbindAll()
        viewFinder.doOnAttach {
            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

            val rotation = viewFinder.display.rotation
            val previewBuild = Preview.Builder()

            val resolutionBuilder = ResolutionSelector.Builder()
            val resolutionSelector = resolutionBuilder.setAllowedResolutionMode(
                PREFER_CAPTURE_RATE_OVER_HIGHER_RESOLUTION
            ).setAspectRatioStrategy(
                AspectRatioStrategy(
                    aspectRatio(viewFinder.width, viewFinder.height),
                    AspectRatioStrategy.FALLBACK_RULE_AUTO
                )
            ).build()

            preview = previewBuild
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(rotation)
                .build()

            // 图像处理
            imageAnalysis = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(rotation)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also {
                    it.setAnalyzer(analyzerExecutor, cameraAnalyzer)
                }
            // 这才是启动开关，从这开始，每帧的图片开始流向图像处理器
            bindCameraLifecycle(cameraSelector)
        }
    }

    private fun bindCameraLifecycle(cameraSelector: CameraSelector) {
        try {
            val viewport = viewFinder.viewPort
            val userCaseGroupBuilder = UseCaseGroup.Builder()
                .addUseCase(preview ?: return)
                .addUseCase(imageAnalysis ?: return)
            viewport?.run {
                userCaseGroupBuilder.setViewPort(viewport)
            }
            camera = cameraProvider?.bindToLifecycle(
                context as ComponentActivity,
                cameraSelector,
                userCaseGroupBuilder.build(),
            )

            preview?.surfaceProvider = viewFinder.surfaceProvider
        } catch (e: Throwable) {
            Log.e(TAG, "config camera exception occur: ${e.message}")
        }
    }

    fun destroyCamera() {
        cameraProvider?.unbindAll()
        captureExecutor.shutdown()
        analyzerExecutor.shutdown()
    }
    /*fun destroyCamera() {
        // 1. 先解绑所有用例
        cameraProvider?.unbindAll()

        // 2. 关闭线程池
        // 这会阻止新的任务被提交，并等待已提交的任务完成
        analyzerExecutor.shutdown()

        // 3. （可选）等待一段时间，确保所有任务都已完成
        try {
            if (!analyzerExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                Log.w(TAG, "Analyzer executor did not terminate in time.")
                // 可以在这里强制关闭线程池，但通常不推荐
                // analyzerExecutor.shutdownNow()
            }
        } catch (e: InterruptedException) {
            analyzerExecutor.shutdownNow()
            Thread.currentThread().interrupt()
        }
    }*/
}
