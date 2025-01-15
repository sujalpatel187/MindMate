package com.example.mindmate


import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BreathingActivity : AppCompatActivity() {

    private lateinit var tvInstruction: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnStart: Button

    private var progress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathing)

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
