package com.example.qrcodeexample.photo.camera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.view.PreviewView
import com.example.qrcodeexample.QrCodeScannerActivity
import com.example.qrcodeexample.R
import com.example.qrcodeexample.manager.CameraMgr
import com.example.qrcodeexample.manager.CameraUseCase
import com.example.qrcodeexample.manager.PermissionManager
import com.example.qrcodeexample.photo.camera.PhotoPreviewActivity

class PhotoCaptureActivity : ComponentActivity() {

    private lateinit var viewFinder: PreviewView
    private lateinit var captureButton: ImageButton

    private lateinit var permissionManager: PermissionManager

    private var mCameraMgr: CameraMgr? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_layout)

        permissionManager = PermissionManager(this)
        initView()

        permissionManager.request(QrCodeScannerActivity.Companion.CAMERA_PERMISSION) { granted ->
            if (granted) {
                startCamera()
            } else {
                Toast.makeText(this, R.string.qrcode_permission_rejected_tip, Toast.LENGTH_LONG).show()
            }
        }

        // checkCameraPermission()
    }

    private fun initView() {
        viewFinder = findViewById(R.id.view_finder_photo)
        captureButton = findViewById(R.id.btn_capture)
        mCameraMgr = CameraMgr(
            this, viewFinder, setOf(CameraUseCase.PREVIEW, CameraUseCase.IMAGE_CAPTURE)
        )

        captureButton.setOnClickListener {
            mCameraMgr?.takePhoto { success, path ->
                if (success) {
                    Toast.makeText(this, "save successfully: $path", Toast.LENGTH_SHORT).show()
                    path?.let {
                        val intent = Intent(this, PhotoPreviewActivity::class.java)
                        intent.putExtra("photo_uri", path)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "save failed: $path", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /*private fun checkCameraPermission() {
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
    }*/

    /*@Deprecated("Deprecated in Java")
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
    }*/

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