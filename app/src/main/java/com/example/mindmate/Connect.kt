package com.example.mindmate

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Collections

class Connect : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var friendName: EditText
    private lateinit var voiceCall: ZegoSendCallInvitationButton
    private lateinit var videoCall: ZegoSendCallInvitationButton
    private lateinit var doctorListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        // Initialize views
        username = findViewById(R.id.user_host)
        friendName = findViewById(R.id.friend_name)
        voiceCall = findViewById(R.id.voicecall)
        videoCall = findViewById(R.id.videocall)
        doctorListView = findViewById(R.id.doctor_list)

        // Set username from intent
        val userNameFromIntent = intent.getStringExtra("username")
        username.text = "Hello 😃 ${userNameFromIntent ?: "Guest"}"

        // List of Doctors
        val doctors = listOf(
            "1. Dr. Alice Johnson",
            "2. Dr. Bob Smith",
            "3. Dr. Carol White",
            "4. Dr. David Brown"
        )

        // Adapter to display the list
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, doctors)
        doctorListView.adapter = adapter

        // Set up click listener for list items
        doctorListView.setOnItemClickListener { _, _, position, _ ->
            // Extract the doctor's name and set it in the EditText
            val selectedDoctor = doctors[position].substringAfter(". ").trim()
            friendName.setText(selectedDoctor)
        }

        // Handle changes in the friendName EditText
        friendName.addTextChangedListener { editable ->
            val target = "doctor"
            if (!target.isNullOrEmpty()) {
                setVoiceCall(target)
                setVideoCall(target)
            }
        }
    }

    private fun setVoiceCall(user: String) {
        voiceCall.setIsVideoCall(false) // Set to voice call
        voiceCall.resourceID = "zego_uikit_call" // Use the proper resource ID
        voiceCall.setInvitees(Collections.singletonList(ZegoUIKitUser(user, user)))
    }

    private fun setVideoCall(user: String) {
        videoCall.setIsVideoCall(true) // Set to video call
        videoCall.resourceID = "zego_uikit_call" // Use the proper resource ID
        videoCall.setInvitees(Collections.singletonList(ZegoUIKitUser(user, user)))
    }
}
