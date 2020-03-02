package com.example.elcare.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R
import kotlinx.android.synthetic.main.activity_currentstatus.*

@SuppressLint("Registered")
class CurrentStatusActivity : AppCompatActivity() {
    lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currentstatus)
        uid = intent.getStringExtra("uid")!!
        val bp = intent.getIntExtra("bp", 0)
        val glucose = intent.getIntExtra("glucose", 0)
        val heartrate = intent.getIntExtra("heartrate", 0)
        heartbeatLevel.apply {
            progress = heartrate.toFloat() / 360 * 100
            text = "${heartrate}beats/min"
        }
        glucoseLevel.apply {
            progress = glucose.toFloat() / 360 * 100
            text = "${glucose}mg/dL"
        }
        bpLevel.apply {
            progress = bp.toFloat() / 360 * 100
            text = "${bp}mmHg"
        }
        detailBtn.setOnClickListener {
            val intent = Intent(this, HealthDetailActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }
}
