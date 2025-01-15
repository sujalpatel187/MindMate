package com.example.mindmate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var logout_user:Button
    lateinit var chatbot:ImageView
    lateinit var news:ImageView
    lateinit var blog:ImageView
    lateinit var podcast:ImageView
    lateinit var confarence:ImageView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    // Declare the TextView for displaying the user's name
    private lateinit var userNameTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        logout_user=findViewById(R.id.logout_user)
        chatbot=findViewById(R.id.chatbot)
        news=findViewById(R.id.news)
        podcast=findViewById(R.id.podcast)
        blog=findViewById(R.id.blog)
        confarence=findViewById(R.id.confarence)

        news.setOnClickListener {
            val intent =Intent(this,NewsWebview::class.java).also {
                startActivity(it)
            }
        }

        blog.setOnClickListener {
            val intent =Intent(this,BlogsWebview::class.java).also {
                startActivity(it)
            }
        }
        podcast.setOnClickListener {
            val intent =Intent(this,PodcasWebview::class.java).also {
                startActivity(it)
            }
        }
        confarence.setOnClickListener {
            val intent =Intent(this,ConfarenceWebview::class.java).also {
                startActivity(it)
            }
        }
        chatbot.setOnClickListener {
            val intent =Intent(this,charbotActivity::class.java).also {
                startActivity(it)
            }
        }

        logout_user.setOnClickListener {
            Firebase.auth.signOut()

            Toast.makeText(this, "Loging Out !!!!", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,LoginUser::class.java).also {
                startActivity(it)
            }
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Get reference to the TextView
        userNameTextView = findViewById(R.id.tv_user_name)

        // Fetch and display the current user's name
        fetchUserName()

        val bottomNavigationView = findViewById<BottomNavigationView>(/* id = */ R.id.bottomNavigationView)

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


    }
    private fun fetchUserName() {
        // Get the currently authenticated user
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Sanitize email (replace dots with commas)
            val emailKey = currentUser.email?.replace(".", ",")

            if (emailKey != null) {
                // Access the user's details in the Realtime Database using the sanitized email as the key
                database.child("Users").child(emailKey).child("name")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // Check if the name exists in the database
                            val name = snapshot.getValue(String::class.java)
                            if (name != null) {
                                // Set the user's name in the TextView
                                userNameTextView.text = "Hello "+name
                            } else {
                                // Handle if name is not found
                                Toast.makeText(this@MainActivity, "Name not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle any errors
                            Toast.makeText(this@MainActivity, "Failed to fetch name: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show()
        }
    }
}