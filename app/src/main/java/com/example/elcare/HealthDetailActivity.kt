package com.example.elcare

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_health_detail.*

class HealthDetailActivity : AppCompatActivity() {
    lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_detail)
        uid = intent.getStringExtra("uid")!!
        view_pager2.adapter = TabAdapter(this)
        Log.i("tab", "adapter set")
        TabLayoutMediator(tab_layout, view_pager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "HeartRate"
                    }
                    1 -> {
                        tab.text = "BP Level"
                    }
                    2 -> {
                        tab.text = "Glucose"
                    }
                }
            }).attach()
    }
}
