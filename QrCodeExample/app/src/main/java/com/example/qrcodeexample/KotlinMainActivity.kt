package com.example.qrcodeexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.qrcodeexample.photo.album.ui.PhotoAlbumActivity
import com.example.qrcodeexample.photo.camera.PhotoCaptureActivity

class KotlinMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        initView()
    }

    private fun initView() {
        // 扫一扫
        findViewById<Button>(R.id.btn_scan).setOnClickListener {
            val intent: Intent = Intent(this, QrCodeScannerActivity::class.java)
            startActivity(intent)
        }
        // 拍摄
        findViewById<Button>(R.id.btn_take_photo).setOnClickListener {
            val intent: Intent = Intent(this, PhotoCaptureActivity::class.java)
            startActivity(intent)
        }
        // 相册
        findViewById<Button>(R.id.btn_photo_album).setOnClickListener {
            val intent: Intent = Intent(this, PhotoAlbumActivity::class.java)
            startActivity(intent)
        }
    }
}

