package com.example.elcare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.ui.HomeActivity
import com.example.elcare.ui.NewUserActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class SigninActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var prefs: SharedPreferences
    val PREFS_NAME: String = "PrefsFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loader.visibility = View.INVISIBLE

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        getPreferenceData()

        signinbtn.setOnClickListener {
            loginuser()
        }
        NoAccountTV.setOnClickListener {
            startsignup()
        }
        signup.setOnClickListener {
            startsignup()
        }
    }

    fun getPreferenceData() {
        val sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sp.contains("prefs_email")) {
            emailSignin.setText(sp.getString("prefs_email", ""))
        }
        if (sp.contains("prefs_pass")) {
            passwordSignin.setText(sp.getString("prefs_pass", ""))
        }
        if (sp.contains("prefs_check")) {
            rememberMe.isChecked = sp.getBoolean("prefs_check", false)
        }

    }

    fun startsignup() {
        val intent = Intent(this, SignupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun loginuser() {
        val email = emailSignin.text.toString().trim()
        val password = passwordSignin.text.toString().trim()
        if (email.isEmpty()) {
            emailSignin.error = "Email Required"
            emailSignin.requestFocus()
            return
        }
        if (!email.contains("@")) {
            emailSignin.error = "Valid Email ID Required"
            emailSignin.requestFocus()
            return
        }
        if (password.isEmpty()) {
            passwordSignin.error = "Password Required"
            passwordSignin.requestFocus()
            return
        }
        loader.visibility = View.VISIBLE
        emailLayout.isEnabled = false
        passwordLayout.isEnabled = false
        signinbtn.isEnabled = false
        rememberMe.isEnabled = false
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (rememberMe.isChecked) {
            val boolIsChecked = rememberMe.isChecked
            val editor = prefs.edit()
            editor.apply {
                putString("prefs_email", emailSignin.text.toString())
                putString("prefs_pass", passwordSignin.text.toString())
                putBoolean("prefs_check", boolIsChecked)
            }
            editor.apply()
        } else {
            prefs.edit().clear().apply()
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loader.visibility = View.GONE
            lateinit var intent: Intent
            val metadata = FirebaseAuth.getInstance().currentUser?.metadata!!
            intent = if (metadata.creationTimestamp == metadata.lastSignInTimestamp) {
                Intent(this, NewUserActivity::class.java)
            } else {
                Intent(this, HomeActivity::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
            .addOnFailureListener {
                emailSignin.text?.clear()
                passwordSignin.text?.clear()
                rememberMe.isChecked = false
                emailLayout.isEnabled = true
                passwordLayout.isEnabled = true
                signinbtn.isEnabled = true
                rememberMe.isEnabled = true
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                loader.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }
}

