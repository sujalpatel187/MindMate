package com.example.mindmate

data class Message(
    var message: String,
    var sentBy: String
) {
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}
