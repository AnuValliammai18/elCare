package com.example.elcare.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R
import com.example.elcare.model.Person
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_user.*

class NewUser : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        val uid = FirebaseAuth.getInstance().uid!!
        create.setOnClickListener {
            val name = name.text.toString()
            val age = age.text.toString()
            val contactName = contactName.toString()
            val contactPhone = contactPhone.toString()
            val contactAddress = email.toString()
            val email = email.text.toString()
            val user =
                Person(age.toInt(), contactAddress, contactPhone.toLong(), contactName, email, name)
            val databaseReference =
                FirebaseDatabase.getInstance().reference
            databaseReference.push().setValue(uid, user)
            Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        val signoutBtn = menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton
        signoutBtn.setBackgroundResource(R.drawable.power_off_foreground)
        signoutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
        }
        return true
    }
}
