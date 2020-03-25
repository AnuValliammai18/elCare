package com.example.elcare.ui

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class Home : Application() {
    override fun onCreate() {
        super.onCreate()
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        lateinit var intent: Intent
        intent = when {
            firebaseUser != null -> {
                Intent(this, HomeActivity::class.java)
            }
            else -> {
                Intent(this, LoginActivity::class.java)
            }
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}