package com.example.qrcodeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.ImageView
import android.net.Uri
import android.widget.Toast

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
