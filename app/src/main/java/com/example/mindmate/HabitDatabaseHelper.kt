package com.example.mindmate.com.example.mindmate

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HabitDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "habitTracker.db"
        private const val DATABASE_VERSION = 1

        // Table and columns
        const val TABLE_HABITS = "habits"
        const val COLUMN_ID = "id"
        const val COLUMN_HABIT_NAME = "habit_name"
        const val COLUMN_START_DATE = "start_date"
        const val COLUMN_END_DATE = "end_date"
        const val COLUMN_FREQUENCY = "frequency"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_HABITS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_HABIT_NAME TEXT, " +
                "$COLUMN_START_DATE TEXT, " +
                "$COLUMN_END_DATE TEXT, " +
                "$COLUMN_FREQUENCY TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HABITS")
        onCreate(db)
    }

    // Add new habit
    fun addHabit(habitName: String, startDate: String, endDate: String, frequency: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HABIT_NAME, habitName)
            put(COLUMN_START_DATE, startDate)
            put(COLUMN_END_DATE, endDate)
            put(COLUMN_FREQUENCY, frequency)
        }
        return db.insert(TABLE_HABITS, null, values)
    }


    // Fetch all habits
    fun getAllHabits(): List<Habit> {
        val habitList = mutableListOf<Habit>()
        val selectQuery = "SELECT * FROM $TABLE_HABITS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val habit = Habit(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HABIT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FREQUENCY))
                )
                habitList.add(habit)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return habitList
    }
}
