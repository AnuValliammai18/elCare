package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.elcare.R
import com.example.elcare.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.title = "User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewmodel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        viewmodel.getPerson()
        viewmodel._person.observe(this, Observer {
            progressBar2.visibility = View.INVISIBLE
            contactTV.visibility = View.VISIBLE
            pic.visibility = View.VISIBLE
            name.text = it.name
            ageTV.text = it.age.toString()
            contactName.text = it.contactname
            email.text = it.email
            contactAddress.text = it.contactAddress
            contactPhone.text = it.contactPhone.toString()
            Glide.with(this).load(it.pic).into(pic)
            showBtn.isEnabled = true
            monitorBtn.isEnabled = true
        })

        showBtn.setOnClickListener {
            val intent = Intent(this, CurrentStatusActivity::class.java)
            startActivity(intent)
        }

        monitorBtn.setOnClickListener {
            val intent = Intent(this, MonitorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
