package com.example.mindmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class RegisterUser : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var reg_btn: Button
    lateinit var login_text: TextView
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent=Intent(this,MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        email=findViewById(R.id.et_email_reg)
        password=findViewById(R.id.et_email_reg)
        reg_btn=findViewById(R.id.btn_reg)
        login_text=findViewById(R.id.tv_login)
        auth = Firebase.auth

        reg_btn.setOnClickListener {
            if(!email.text.toString().equals(null) && !password.text.toString().equals(null)){
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Login Done",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val user = auth.currentUser

                            val intent= Intent(this,UserDetailsActivity::class.java).also {
                                startActivity(it)
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
    }
}