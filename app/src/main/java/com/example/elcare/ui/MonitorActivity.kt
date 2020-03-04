package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R
import com.example.elcare.model.HealthRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_monitor.*

class MonitorActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)
        val uid = FirebaseAuth.getInstance().uid!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        upload.setOnClickListener {
            val bp = bpText.text.toString()
            val glucose = glucoseText.text.toString()
            val heartBeat = heartBeatText.text.toString()
            val healthRecord = HealthRecord(
                bp.toInt(),
                glucose.toInt(),
                heartBeat.toInt()
            )
            val databaseReference =
                FirebaseDatabase.getInstance().reference.child(uid).child("HealthRecord")
            databaseReference.push().setValue(healthRecord)
            Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show()
            bpText.text.clear()
            glucoseText.text.clear()
            heartBeatText.text.clear()
            startActivity(Intent(this, UserDetailActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}
