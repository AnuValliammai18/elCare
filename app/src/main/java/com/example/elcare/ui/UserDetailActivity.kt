package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R
import com.example.elcare.model.HealthRecord
import com.example.elcare.model.Person
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetailActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val dbref = database.reference
    lateinit var uid: String
    lateinit var healthRecord: HealthRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        uid = FirebaseAuth.getInstance().uid!!
        dbref.child(uid)
        if (uid == "CyXcFYsmt7NV3UcPpHIEarI7nLh2") {
            startActivity(Intent(this, NewUser::class.java))
        } else {
            val personListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val p = p0.child(uid).getValue(Person::class.java)
                    p?.let {
                        name.text = it.name
                        ageTV.text = it.age.toString()
                        contactName.text = it.contactname
                        email.text = it.contactAddress
                        contactPhone.text = it.contactPhone.toString()
                        val health = p0.child(uid).child("HealthRecord").children.last()
                        val h = health.getValue(HealthRecord::class.java)
                        healthRecord = h!!
                    }
                }
            }
            dbref.addValueEventListener(personListener)
        }
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
