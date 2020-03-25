package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.elcare.R
import com.example.elcare.viewmodel.UserDetailViewModel
import com.firebase.ui.auth.AuthUI
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
            progressBar2.visibility = View.VISIBLE
            contactTV.visibility = View.VISIBLE
            pic.visibility = View.VISIBLE
            name.text = it.name
            ageTV.text = it.age.toString()
            contactName.text = it.contactname
            email.text = it.email
            contactAddress.text = it.contactAddress
            contactPhone.text = it.contactPhone.toString()
            Glide.with(this).load(it.pic).into(pic)
            show.isEnabled = true
            monitor.isEnabled = true
        })

        show.setOnClickListener {
            val intent = Intent(this, CurrentStatusActivity::class.java)
            startActivity(intent)
        }

        monitor.setOnClickListener {
            val intent = Intent(this, MonitorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        val signoutBtn = (menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton).also {
            it.setBackgroundResource(R.drawable.power_off_foreground)
        }
        signoutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}
