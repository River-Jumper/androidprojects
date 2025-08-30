package com.example.qrcodeexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class KotlinMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.btn_scan).setOnClickListener {
            val intent: Intent = Intent(this, QrCodeScannerActivity::class.java)
            startActivity(intent)
        }
    }
}

