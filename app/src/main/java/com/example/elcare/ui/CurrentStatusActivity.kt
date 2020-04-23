package com.example.elcare.ui


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.elcare.R
import com.example.elcare.viewmodel.HeathDetailViewModel
import kotlinx.android.synthetic.main.activity_currentstatus.*

class CurrentStatusActivity : AppCompatActivity() {
    var CHANNEL_ID = "channel1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currentstatus)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Current Status"

        clock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

        val notificationManager = NotificationManagerCompat.from(this)

        val viewmodel = ViewModelProvider(this).get(HeathDetailViewModel::class.java)
        viewmodel.getCurrentHealthStatus()
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
            var message: String? = null
            if (it.heartRate > 100 || it.heartRate < 60) {
                message =
                    if (it.heartRate < 60) "Sudden drop in Heart Rate" else "Sudden rise in Heart Rate"
            } else if (it.glucose < 160 || it.glucose > 240) {
                message =
                    if (it.glucose < 160) "Sudden drop in Glucose Level" else "Sudden rise in Glucose Level"
            } else if (it.bp > 180 || it.bp < 120) {
                message =
                    if (it.glucose < 160) "Sudden drop in Glucose Level" else "Sudden rise in Glucose Level"
            }
            if (message != null) {
                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Emergency")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build()
                notificationManager.notify(1, notification)
            }
        })

        detailBtn.setOnClickListener {
            detailBtn.isClickable = false
            val intent = Intent(this, HealthDetailActivity::class.java)
            startActivity(intent)
            detailBtn.isClickable = true

        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}
