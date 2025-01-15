package com.example.mindmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileDActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var logout_user: Button

    // TextViews to display user details
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var sexTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_dactivity)

        logout_user=findViewById(R.id.logout_user)
        logout_user.setOnClickListener {
            Firebase.auth.signOut()

            Toast.makeText(this, "Loging Out !!!!", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,LoginUser::class.java).also {
                startActivity(it)
            }
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(/* id = */ R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.profile

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
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Initialize TextViews
        nameTextView = findViewById(R.id.tv_name)
        emailTextView = findViewById(R.id.tv_email)
        ageTextView = findViewById(R.id.tv_age)
        sexTextView = findViewById(R.id.tv_sex)
        phoneTextView = findViewById(R.id.tv_phone)
        addressTextView = findViewById(R.id.tv_address)

        // Fetch and display user details
        fetchUserDetails()

    }
    private fun fetchUserDetails() {
        // Get the currently authenticated user
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Sanitize email (replace dots with commas) for database key
            val emailKey = currentUser.email?.replace(".", ",")

            if (emailKey != null) {
                // Access the user's details in Firebase Realtime Database
                database.child("Users").child(emailKey)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // Check if data exists
                            if (snapshot.exists()) {
                                // Retrieve user details
                                val name =
                                    snapshot.child("name").getValue(String::class.java) ?: "N/A"
                                val email =
                                    snapshot.child("email").getValue(String::class.java) ?: "N/A"
                                val age =
                                    snapshot.child("age").getValue(String::class.java) ?: "N/A"
                                val sex =
                                    snapshot.child("sex").getValue(String::class.java) ?: "N/A"
                                val phone =
                                    snapshot.child("phone").getValue(String::class.java) ?: "N/A"
                                val address =
                                    snapshot.child("address").getValue(String::class.java) ?: "N/A"

                                // Set values to TextViews
                                nameTextView.text = "Name : "+name
                                emailTextView.text = "Email id : "+email
                                ageTextView.text = "Age : "+age
                                sexTextView.text = "Sex : "+sex
                                phoneTextView.text = "Phone No. : "+phone
                                addressTextView.text = "Address : "+address
                            } else {
                                Toast.makeText(
                                    this@ProfileDActivity,
                                    "User details not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@ProfileDActivity,
                                "Failed to fetch user details: ${error.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show()
        }
    }
}