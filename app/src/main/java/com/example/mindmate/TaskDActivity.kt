package com.example.mindmate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class TaskDActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_dactivity)

        val bottomNavigationView = findViewById<BottomNavigationView>(/* id = */ R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.task

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "User Dashboard", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.emotion -> {
                    startActivity(Intent(this, EmotionDActivity::class.java))
                    Toast.makeText(this, "User Breathing", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.docter -> {
                    startActivity(Intent(this, DoctorDActivity::class.java))
                    Toast.makeText(this, "User Doctor", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.task -> {
                    startActivity(Intent(this, TaskDActivity::class.java))
                    Toast.makeText(this, "User Task", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileDActivity::class.java))
                    Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

//        val webView = findViewById<WebView>(R.id.webView_task)
//
//        // Enable JavaScript in WebView
//        val webSettings: WebSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//
//        // Ensure links open in the WebView instead of an external browser
//        webView.webViewClient = WebViewClient()
//
//        // Load the URL
//        webView.loadUrl("https://mindmate-tozf.onrender.com/exercise ")
    }
}