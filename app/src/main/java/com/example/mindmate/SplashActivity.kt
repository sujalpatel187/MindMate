package com.example.mindmate
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkBiometricSupport()
    }

    private fun checkBiometricSupport() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> authenticateUser()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(this, "No biometric hardware available", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(this, "Biometric hardware is currently unavailable", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(this, "No biometric data enrolled", Toast.LENGTH_SHORT).show()
            else ->
                Toast.makeText(this, "Biometric authentication not supported", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authenticateUser() {
        val executor: Executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@SplashActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@SplashActivity, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                // Navigate to the main activity
                val intent = Intent(this@SplashActivity, LoginUser::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@SplashActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use your fingerprint or face to unlock the app")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}