package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.elcare.R
import kotlinx.android.synthetic.main.activity_currentstatus.*

class CurrentStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currentstatus)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewmodel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        viewmodel.getPerson()
        viewmodel._healthrecord.observe(this, Observer {
            heartbeatLevel.apply {
                progress = it.heartRate.toFloat() / 360 * 100
                text = "${it.heartRate}beats/min"
            }
            glucoseLevel.apply {
                progress = it.glucose.toFloat() / 360 * 100
                text = "${it.glucose}mg/dL"
            }
            bpLevel.apply {
                progress = it.bp.toFloat() / 360 * 100
                text = "${it.bp}mmHg"
            }
        })

        detailBtn.setOnClickListener {
            val intent = Intent(this, HealthDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}
