package com.example.elcare

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.ui.HomeActivity
import com.example.elcare.ui.NewUserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.title = "Sign Up"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar.visibility = View.INVISIBLE

        signupbtn.setOnClickListener {
            createUser()
        }
        alreadyMember.setOnClickListener {
            finish()
        }
        signin.setOnClickListener {
            finish()
        }
    }

    fun createUser() {
        val email = emailSignup.text.toString().trim()
        val password = passwordSignup.text.toString().trim()
        val fullname = nameSignup.text.toString().trim()
        if (fullname.isEmpty()) {
            nameSignup.error = "Fullname Required"
            nameSignup.requestFocus()
            return
        }
        if (email.isEmpty()) {
            emailSignup.error = "Email Required"
            emailSignup.requestFocus()
            return
        }
        if (!email.contains("@")) {
            emailSignup.error = "Valid Email ID Required"
            emailSignup.requestFocus()
            return
        }
        if (password.isEmpty()) {
            passwordSignup.error = "Password Required"
            passwordSignup.requestFocus()
            return
        }
        if (password.length < 6) {
            passwordSignup.error = "Password should be atleast 6 character long"
            passwordSignup.requestFocus()
            return
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        fullnameLayout.isEnabled = false
        newemailLayout.isEnabled = false
        newpasswordLayout.isEnabled = false
        signupbtn.isEnabled = false
        progressBar.visibility = View.VISIBLE

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            progressBar.visibility = View.GONE
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
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                nameSignup.text?.clear()
                emailSignup.text?.clear()
                passwordSignup.text?.clear()
                fullnameLayout.isEnabled = true
                newemailLayout.isEnabled = true
                newpasswordLayout.isEnabled = true
                signupbtn.isEnabled = true
                progressBar.visibility = View.GONE
                if (it is FirebaseAuthUserCollisionException) {
                    emailSignup.text?.clear()
                    passwordSignup.text?.clear()
                    nameSignup.text?.clear()
                    emailSignup.error = "This Account already exists."
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}
