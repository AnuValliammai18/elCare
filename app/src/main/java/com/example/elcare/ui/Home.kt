package com.example.elcare.ui

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class Home : Application() {
    override fun onCreate() {
        super.onCreate()
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val uid = firebaseAuth.uid
        lateinit var intent: Intent
        if (uid == "CyXcFYsmt7NV3UcPpHIEarI7nLh2") {
            intent = Intent(this, NewUser::class.java)
        } else if (firebaseUser != null) {
            intent = Intent(this, UserDetailActivity::class.java)
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}