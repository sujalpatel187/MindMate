package com.example.mindmate

import android.content.Intent
import android.os.Bundle
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
import java.util.MissingFormatArgumentException
import kotlin.math.log

class LoginUser : AppCompatActivity() {

    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var login_btn:Button
    lateinit var reg_text:TextView
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
        setContentView(R.layout.activity_login_user)

        email=findViewById(R.id.et_email_login)
        password=findViewById(R.id.et_email_login)
        login_btn=findViewById(R.id.btn_login)
        reg_text=findViewById(R.id.tv_register)
        auth = Firebase.auth


        reg_text.setOnClickListener {
            val intent=Intent(this,RegisterUser::class.java).also {
                startActivity(it)
            }
        }


        login_btn.setOnClickListener {
            if (!email.text.toString().equals(null) && !password.text.toString().equals(null)) {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(
                                this,
                                "Login Done",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent= Intent(this,MainActivity::class.java).also {
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