package com.example.elcare.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class Home : Application() {
    var CHANNEL_ID = "channel1"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel1 = NotificationChannel(
                CHANNEL_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");


            val manager = getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel1)
        }
    }
}