package com.example.elcare.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.elcare.R
import com.example.elcare.adapters.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_health_detail.*

class HealthDetailActivity : AppCompatActivity() {
    lateinit var viewModel: UserDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        viewModel.getHealthRecord()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}
