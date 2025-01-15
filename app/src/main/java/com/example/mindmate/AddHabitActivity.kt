package com.example.mindmate


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mindmate.com.example.mindmate.HabitDatabaseHelper

class AddHabitActivity : AppCompatActivity() {

    private lateinit var etHabitName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etFrequency: EditText
    private lateinit var btnAddHabit: Button

    private lateinit var dbHelper: HabitDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        dbHelper = HabitDatabaseHelper(this)

        etHabitName = findViewById(R.id.etHabitName)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        etFrequency = findViewById(R.id.etFrequency)
        btnAddHabit = findViewById(R.id.btnAddHabit)

        btnAddHabit.setOnClickListener {
            addNewHabit()
        }
    }

    private fun addNewHabit() {
        val habitName = etHabitName.text.toString().trim()
        val startDate = etStartDate.text.toString().trim()
        val endDate = etEndDate.text.toString().trim()
        val frequency = etFrequency.text.toString().trim()

        if (habitName.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || frequency.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val habitId = dbHelper.addHabit(habitName, startDate, endDate, frequency)

        if (habitId > -1) {
            Toast.makeText(this, "Habit added successfully", Toast.LENGTH_SHORT).show()
            // Navigate back to HabitTrackerActivity
            startActivity(Intent(this, HabitTrackerActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Error adding habit", Toast.LENGTH_SHORT).show()
        }
    }
}
