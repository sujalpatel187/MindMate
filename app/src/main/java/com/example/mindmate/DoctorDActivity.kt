package com.example.mindmate

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig

class DoctorDActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var profilename:String

    // Declare the TextView for displaying the user's name
    private lateinit var userNameTextView: TextView

    lateinit var login: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_dactivity)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(/* id = */ R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.docter

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

        // Get reference to the TextView
        userNameTextView = findViewById(R.id.textView)

        // Fetch and display the current user's name
        fetchUserName()
        userNameTextView.text="Hello : "+userNameTextView.text
//        checkAndRequestAutoStartPermission()

        // Check and request lock screen notification permission
        checkAndRequestLockScreenNotificationPermission()

        // Check and request overlay permission (display popups)
        checkAndRequestOverlayPermission()

        login = findViewById(R.id.login)

        login.setOnClickListener {
            val user = "Paitent";
            val config= ZegoUIKitPrebuiltCallInvitationConfig()
            ZegoUIKitPrebuiltCallService.init(application,
                str.appid.toLong(), str.appsign,user,user,config)

            val intent = Intent(this,Connect::class.java)
            intent.putExtra("username",user)
            startActivity(intent)
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
                                userNameTextView.text = name
                                profilename=name.toString()
                            } else {
                                // Handle if name is not found
                                Toast.makeText(this@DoctorDActivity, "Name not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle any errors
                            Toast.makeText(this@DoctorDActivity, "Failed to fetch name: ${error.message}", Toast.LENGTH_SHORT).show()
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
    private fun checkAndRequestAutoStartPermission() {
        val manufacturer = Build.MANUFACTURER.lowercase()
        if (manufacturer.contains("xiaomi") || manufacturer.contains("oppo") || manufacturer.contains("vivo")) {
            // Direct the user to the specific settings page
            try {
                val intent = Intent()
                when (manufacturer) {
                    "xiaomi" -> {
                        intent.component = android.content.ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity"
                        )
                    }
                    "oppo" -> {
                        intent.component = android.content.ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                        )
                    }
                    "vivo" -> {
                        intent.component = android.content.ComponentName(
                            "com.vivo.permissionmanager",
                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                        )
                    }
                }
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Please enable auto-start for this app.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkAndRequestLockScreenNotificationPermission() {
        val notificationManager = NotificationManagerCompat.from(this)
        if (!notificationManager.areNotificationsEnabled()) {
            Toast.makeText(this, "Please enable notifications for lock screen visibility.", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
        }
    }

    private fun checkAndRequestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // Request permission
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                Toast.makeText(this, "Please enable 'Display over other apps' permission.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }
    }
}