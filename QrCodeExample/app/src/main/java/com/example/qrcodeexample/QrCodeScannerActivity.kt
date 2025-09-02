package com.example.qrcodeexample

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.qrcodeexample.manager.CameraMgr
import com.example.qrcodeexample.manager.CameraUseCase
import com.example.qrcodeexample.manager.PermissionManager

/**
 * day1
 * 1、认识 Activity 和 fragment，了解对应的生命周期
 * 2、认识 res 资源目录
 *    ·认识 Layout 目录下的 XML 文件、了解常用的布局和控件（ConstraintLayout、AppCompatImageView、AppCompatTextview等）
 *    ·了解图片、文字、颜色资源放置点
 *    ·了解 -xxh、-night 等限定符，了解正常模式、暗黑模式资源切换原理
 * 3、认识 AndroidManifest 文件
 *    ·修改应用图标和应用名称
 *    ·认识 Activity、权限声明
 * 4、认识 build.gradle 文件
 *    ·依赖导入方式
 *    ·编译配置
 * 5、认识权限申请流程，了解运行时权限
 * 6、认识 LOG 打印及等级划分，认识 Toast 使用
 * 7、debug 相机启动流程和解析流程，认识 debug 面板，熟用f7、f8、evaluate 功能。
 */
class QrCodeScannerActivity : ComponentActivity() {
    // 类似于cpp的在顶层定义常量，但是这个是在类的顶层，不用担心名空间的事情
    // 这个类是“伴生类”，在单例和单独一个类的工厂方法的实现中比较有用（是kotlin的语法糖）
    // 访问这样不具名的伴生类，可以直接通过<外部类名>.<伴生类中的成员>
    // 理由：所有在伴生类中的成员都被视为外部类的静态成员
    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 100
        const val CAMERA_PERMISSION = "android.permission.CAMERA"
    }

    private var mCameraMgr: CameraMgr? = null

    private lateinit var permissionManager: PermissionManager

    private inner class CameraMgrCreateNewWebListener : CameraMgr.CreateNewWebListener {
        override fun createNewWeb(url: String?) {
            // mCameraMgr?.destroyCamera()

            val intent = Intent(this@QrCodeScannerActivity, WebViewActivity::class.java).apply {
                putExtra("url", url)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_layout)
        mCameraMgr = CameraMgr(this
            , this.findViewById(R.id.view_finder)
            , setOf(CameraUseCase.PREVIEW, CameraUseCase.IMAGE_ANALYSIS)).apply {
            createNewWebListener = CameraMgrCreateNewWebListener()
        }
        permissionManager = PermissionManager(this)

        permissionManager.request(QrCodeScannerActivity.CAMERA_PERMISSION) {
            isGranted ->
            if (isGranted) {
                startCamera()
            }
            else {
                Toast.makeText(this, R.string.qrcode_permission_rejected_tip, Toast.LENGTH_LONG).show()
            }
        }

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        when {
            // 权限已授予
            ContextCompat.checkSelfPermission(
                this,
                CAMERA_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }

            // 申请相机权限
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(CAMERA_PERMISSION),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    // 权限申请结果回调
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予权限，执行相机操作
                startCamera()
            } else {
                Toast.makeText(this, R.string.qrcode_permission_rejected_tip, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun startCamera() {
        try {
            mCameraMgr?.startCamera()
        } catch (e: Exception) {
            Log.e("KotlinMainActivity", "Failed to start camera", e)
            finish()
        }
    }

    override fun onDestroy() {
        mCameraMgr?.destroyCamera()
        super.onDestroy()
    }
}