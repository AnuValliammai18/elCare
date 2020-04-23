package com.example.elcare

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.ui.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    val firebaseAuth = FirebaseAuth.getInstance()
                    val firebaseUser = firebaseAuth.currentUser
                    lateinit var intent: Intent
                    intent = when {
                        firebaseUser != null -> {
                            Intent(baseContext, HomeActivity::class.java)
                        }
                        else -> {
                            Intent(baseContext, SigninActivity::class.java)
                        }
                    }
                    startActivity(intent)
                    overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()

    }
}
