package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elcare.R
import com.example.elcare.SigninActivity
import com.example.elcare.ui.fragments.AboutFragment
import com.example.elcare.ui.fragments.TipsFragment
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class HomeActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.tipsFragment,
                R.id.userDetailActivity,
                R.id.currentStatusActivity,
                R.id.healthDetailActivity,
                R.id.monitorActivity,
                R.id.signinActivity,
                R.id.aboutFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        val signoutBtn = (menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton).also {
            it.setBackgroundResource(R.drawable.power_off_foreground)
        }
        signoutBtn.setOnClickListener {
            signout()
        }
        return true
    }

    fun signout() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setCancelable(false)
            setPositiveButton(
                "Logout"
            ) { _, _ ->
                AuthUI.getInstance().signOut(this@HomeActivity)
                    .addOnCompleteListener {
                        startActivity(Intent(this@HomeActivity, SigninActivity::class.java))
                        this@HomeActivity.finish()
                    }
            }
            setNegativeButton("No") { _, _ ->
            }
        }
        builder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.tipsFragment -> {
                fragment = TipsFragment()
            }
            R.id.userDetailActivity -> {
                startActivity(Intent(this, UserDetailActivity::class.java))
            }
            R.id.currentStatusActivity -> {
                startActivity(Intent(this, CurrentStatusActivity::class.java))
            }
            R.id.healthDetailActivity -> {
                startActivity(Intent(this, HealthDetailActivity::class.java))
            }
            R.id.monitorActivity -> {
                startActivity(Intent(this, MonitorActivity::class.java))
            }
            R.id.signinActivity -> {
                signout()
            }
            R.id.aboutFragment -> {
                fragment = AboutFragment()
            }

        }
        if (fragment != null) {
            val ft =
                supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, fragment)
            ft.commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
