package com.example.qrcodeexample.photo.camera

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.qrcodeexample.R

class PhotoPreviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)

        val imageView = findViewById<ImageView>(R.id.photoPreview)

        // 拿到传过来的 uri
        val photoUri = intent.getStringExtra("photo_uri")

        if (photoUri != null) {
            imageView.setImageURI(Uri.parse(photoUri))
        } else {
            Toast.makeText(this, "未找到照片", Toast.LENGTH_SHORT).show()
        }
    }
}