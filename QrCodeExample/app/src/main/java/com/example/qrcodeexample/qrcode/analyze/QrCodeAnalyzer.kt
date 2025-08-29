package com.example.qrcodeexample.qrcode.analyze

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy


/**
 * day2
 * 1、认识解析流程
 * 2、在网页生成一个百度链接的二维码，扫描并在解析中获取 URL 链接
 * 3、新起一个 webView，在该控件中打开 URL链接
 * 4、了解 webView 安全漏洞和 JS 交互实现
 */
class QrCodeAnalyzer {
    companion object {
        const val TAG: String = "QrCodeAnalyzer"
    }

    private var reusableBitmap: Bitmap? = null
    private var paused = false

    var result: String? = null

    interface ResultListener {
        fun onResultGet(result: String?)
    }

    var resultListener: ResultListener? = null

    fun analyze(image: ImageProxy) {
        // 识别二维码成功后就停止逐帧分析图像了
        if (paused) {
            // 可以在这里做一些其他的事情，比如打开这个二维码网站
            // 硬关闭相机，而不是用pause软关闭
            // 两种方式：第一种是写回调关闭相机
            // 第二种是切换到主线程，直接关闭相机
            // 这里选了第一种，因为这个是普通类，不想传上下文


            return
        }
        val tempBitmap = covertToBitmap(image)
        tempBitmap?.let {
            detectImage(it)
        }
    }

    private fun covertToBitmap(proxy: ImageProxy): Bitmap? {
        var bitmap: Bitmap? = null
        proxy.runCatching {
            val plane = planes.firstOrNull() ?: return null
            val pixelStride: Int = plane.pixelStride
            val rowStride: Int = plane.rowStride
            val rowPadding = rowStride - pixelStride * proxy.width
            val width = proxy.width + rowPadding / pixelStride
            val height = proxy.height
            bitmap = getBitmap(width, height)
            bitmap?.copyPixelsFromBuffer(plane.buffer)
        }
        return bitmap
    }

    private fun getBitmap(width: Int, height: Int): Bitmap? {
        reusableBitmap = when {
            reusableBitmap == null
                    || reusableBitmap?.width != width
                    || reusableBitmap?.height != height -> {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }

            else -> reusableBitmap
        }
        return reusableBitmap
    }

    // 这里：拿到二维码扫描的结果之后就没有后续了，只是输出到了日志中
    // 可以将这个result设置为类的成员变量，然后自己对result进行后续的处理
    // ?.let是一个解包null的kotlin语法糖
    private fun detectImage(bitmap: Bitmap) {
        result = QRCodeDecoder.syncDecodeQRCode(bitmap) ?: return
        result?.let {
            Log.e(TAG, it)
            paused = true
            resultListener?.onResultGet(result)
        }
    }

}