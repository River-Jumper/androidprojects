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

        // 检查 URL 是否为空
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "无效的网址", Toast.LENGTH_LONG).show()
            // 如果 URL 无效，直接返回，不执行下面的加载操作
            return
        }
        // ✅ URL 有效，在这里加载网页
        webView.loadUrl(url)
    }
}