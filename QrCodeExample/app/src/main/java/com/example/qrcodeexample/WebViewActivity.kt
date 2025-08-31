package com.example.qrcodeexample

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra("url")

        webView = findViewById(R.id.webview)
        webView.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true
        }
        webView.webViewClient = WebViewClient()

        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "无效的网址", Toast.LENGTH_LONG).show()
            return
        }
        webView.loadUrl(url)
    }
}