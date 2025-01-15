package com.example.mindmate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmotionDActivity : AppCompatActivity() {

    private lateinit var tvInstruction: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnStart: Button

    private var progress = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_dactivity)

        val bottomNavigationView = findViewById<BottomNavigationView>(/* id = */ R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.emotion

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
        tvInstruction = findViewById(R.id.tvInstruction)
        progressBar = findViewById(R.id.progressBar)
        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener {
            startBreathingExercise()
        }
    }
    private fun startBreathingExercise() {
        val totalBreathCycle = 5  // Number of cycles (breathe in, hold, breathe out)
        val totalTime = 6000L  // 6 seconds for each phase

        object : CountDownTimer((totalBreathCycle * totalTime), 50) {

            override fun onTick(millisUntilFinished: Long) {
                val timeLeftInCycle = millisUntilFinished % totalTime

                when {
                    timeLeftInCycle <= 2000 -> {
                        tvInstruction.text = "Breathe In"
                        updateProgress(timeLeftInCycle, 2000)
                    }
                    timeLeftInCycle <= 4000 -> {
                        tvInstruction.text = "Hold"
                        updateProgress(timeLeftInCycle - 2000, 2000)
                    }
                    else -> {
                        tvInstruction.text = "Breathe Out"
                        updateProgress(timeLeftInCycle - 4000, 2000)
                    }
                }
            }

            override fun onFinish() {
                tvInstruction.text = "Done!"
                progressBar.progress = 100
            }
        }.start()
    }

    private fun updateProgress(timeLeft: Long, duration: Long) {
        progress = ((timeLeft.toFloat() / duration) * 100).toInt()
        progressBar.progress = 100 - progress
    }
}