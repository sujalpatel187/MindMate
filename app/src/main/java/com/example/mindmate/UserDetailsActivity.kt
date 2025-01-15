package com.example.mindmate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var sexEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var submitButton: Button

    // Firebase Auth & Realtime Database reference
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        // Initialize Firebase Auth & Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Initialize views
        nameEditText = findViewById(R.id.et_name)
        emailEditText = findViewById(R.id.et_email)
        ageEditText = findViewById(R.id.et_age)
        sexEditText = findViewById(R.id.et_sex)
        phoneEditText = findViewById(R.id.et_phone)
        passwordEditText = findViewById(R.id.et_password)
        addressEditText = findViewById(R.id.et_address)
        submitButton = findViewById(R.id.btn_submit)

        // Set up the submit button listener
        submitButton.setOnClickListener {
            saveUserDetails()
        }
    }

    private fun saveUserDetails() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val age = ageEditText.text.toString().trim()
        val sex = sexEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()

        // Validate fields (e.g., empty fields, valid email, etc.)
        if (name.isEmpty() || email.isEmpty() || age.isEmpty() || sex.isEmpty() ||
            phone.isEmpty() || password.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Firebase requires email to be a valid key (replace dots with commas or another character)
        val sanitizedEmail = email.replace(".", ",")

        // Create a User data class instance
        val user = User(name, email, age, sex, phone, password, address)

        // Store user details in Firebase Realtime Database under the sanitized email
        database.child("Users").child(sanitizedEmail).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User details saved successfully", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,LoginUser::class.java).also {
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, "Failed to save user details", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

// Data class to hold user details
data class User(
    val name: String,
    val email: String,
    val age: String,
    val sex: String,
    val phone: String,
    val password: String,
    val address: String
)

class MessageAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return MyViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        } else {
            holder.rightChatView.visibility = View.GONE
            holder.leftChatView.visibility = View.VISIBLE
            holder.leftTextView.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        val rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        val leftTextView: TextView = itemView.findViewById(R.id.left_chat_text_view)
        val rightTextView: TextView = itemView.findViewById(R.id.right_chat_text_view)
    }
}