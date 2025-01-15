package com.example.mindmate

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsWebview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_webview)
        val webView = findViewById<WebView>(R.id.webView_news)

        // Enable JavaScript in WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Ensure links open in the WebView instead of an external browser
        webView.webViewClient = WebViewClient()

        // Load the URL
        webView.loadUrl("https://www.sciencedaily.com/news/mind_brain/")
    }
}