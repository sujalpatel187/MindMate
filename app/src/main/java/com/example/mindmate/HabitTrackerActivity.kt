package com.example.mindmate


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmate.com.example.mindmate.HabitAdapter
import com.example.mindmate.com.example.mindmate.HabitDatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitTrackerActivity : AppCompatActivity() {

    private lateinit var rvHabits: RecyclerView
    private lateinit var fabAddHabit: FloatingActionButton
    private lateinit var dbHelper: HabitDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_tracker)

        dbHelper = HabitDatabaseHelper(this)

        rvHabits = findViewById(R.id.rvHabits)
        fabAddHabit = findViewById(R.id.fabAddHabit)

        rvHabits.layoutManager = LinearLayoutManager(this)

        // Load habits from the database
        loadHabits()

        fabAddHabit.setOnClickListener {
            // Start the add habit activity
            startActivity(Intent(this, AddHabitActivity::class.java))
        }
    }

    private fun loadHabits() {
        val habitList = dbHelper.getAllHabits()
        val adapter = HabitAdapter(habitList)
        rvHabits.adapter = adapter
    }
}
