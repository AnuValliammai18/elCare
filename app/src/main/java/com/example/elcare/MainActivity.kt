package com.example.elcare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R.drawable
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.FacebookBuilder().build()
    )
    private val database = FirebaseDatabase.getInstance()
    private val dbref = database.reference
    lateinit var uid: String
    lateinit var healthRecord: HealthRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userSignIn()

        show.setOnClickListener {
            val intent = Intent(this, CurrentStatusActivity::class.java)
            intent.apply {
                putExtra("bp", healthRecord.bp)
                putExtra("glucose", healthRecord.glucose)
                putExtra("heartrate", healthRecord.heartRate)
                putExtra("uid", uid)
            }
            startActivity(intent)
        }

        monitor.setOnClickListener {
            val intent = Intent(this, MonitorActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }

    private fun userSignIn() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(true)
                .setAvailableProviders(providers)
                .setLogo(drawable.elcare)
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        val signoutBtn = menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton
        signoutBtn.setBackgroundResource(R.drawable.power_off_foreground)
        signoutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener { userSignIn() }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                uid = FirebaseAuth.getInstance().uid!!
                if (uid != "CyXcFYsmt7NV3UcPpHIEarI7nLh2") {
                    //   dbref.child("oIF0FJJ0E1VotdufQNPnCENSlSG2")
                    val personListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (person in p0.children) {
                                val p = person.getValue(Person::class.java)
                                p?.let {
                                    name.text = it.name
                                    age.text = it.age.toString()
                                    contactName.text = it.contactname
                                    contactAddress.text = it.contactAddress
                                    contactPhone.text = it.contactPhone.toString()
                                }
                                val health = person.child("HealthRecord").children.last()
                                val h = health.getValue(HealthRecord::class.java)
                                healthRecord = h!!
                            }
                        }
                    }
                    dbref.addValueEventListener(personListener)
                }
            } else {
                Toast.makeText(this, response?.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
