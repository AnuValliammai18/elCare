package com.example.elcare.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elcare.R
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.blankFragment,
                R.id.userDetailActivity,
                R.id.currentStatusActivity,
                R.id.healthDetailActivity,
                R.id.monitorBtn,
                R.id.loginActivity,
                R.id.aboutFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /* val homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
         homeViewModel.getDetail()
         homeViewModel._name.observe(this,Observer{
             navName.text = it
         })
         homeViewModel._email.observe(this,Observer{
             navMail.text =it
         })
         homeViewModel._photoUrl.observe(this,Observer{
             Glide.with(this).load(it).into(navImageView)
         })*/
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
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}