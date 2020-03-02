package com.example.elcare

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_monitor.*

class MonitorActivity : AppCompatActivity() {

    lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)
        uid = intent.getStringExtra("uid")!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        upload.setOnClickListener {
            val bp = bpText.text.toString()
            val glucose = glucoseText.text.toString()
            val heartBeat = heartBeatText.text.toString()
            val healthRecord = HealthRecord(bp.toInt(), glucose.toInt(), heartBeat.toInt())
            val databaseReference =
                FirebaseDatabase.getInstance().reference.child(uid).child("HealthRecord")
            databaseReference.push().setValue(healthRecord)
            Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show()
            bpText.text.clear()
            glucoseText.text.clear()
            heartBeatText.text.clear()
        }
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }
}
