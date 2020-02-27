package com.example.elcare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elcare.R.drawable
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN = 1
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.FacebookBuilder().build()
    )
    val database= FirebaseDatabase.getInstance()
    val dbref= database.reference
    val dbrefhealth= database.reference
    lateinit var healthRecord:HealthRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userSignIn()

        show.setOnClickListener {
            val intent=Intent(this,SecondActivity::class.java)
            intent.putExtra("bp",healthRecord.bp)
            intent.putExtra("glucose",healthRecord.glucose)
            intent.putExtra("heartrate",healthRecord.heartRate)
            Log.i("database",healthRecord.toString())
            startActivity(intent)
        }
    }

    private fun userSignIn() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(true)
                .setAvailableProviders(providers)
                .setLogo(drawable.elcare)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        val signoutBtn=menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton
        signoutBtn.setBackgroundResource(R.drawable.power_off_foreground)
        signoutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener { userSignIn() }
        }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val uid = FirebaseAuth.getInstance().uid
                dbref.child(uid!!)
                val personListener=object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        for(person in p0.children) {
                            val p=person.getValue(Person::class.java)!!
                            name.text=p.name
                            age.text=p.age.toString()
                            contactName.text=p.contactname
                            contactAddress.text=p.contactAddress
                            contactPhone.text=p.contactPhone.toString()
                        }
                    }
                }
                dbref.addValueEventListener(personListener)
                dbrefhealth.child(uid).child("HealthRecord")
                val a=dbrefhealth
                val healthrecordListener=object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                       val h=p0.getValue(HealthRecord::class.java)
                        Log.i("database",h.toString())
                        healthRecord=h!!
                    }
                }
                dbrefhealth.addValueEventListener(healthrecordListener)
            } else {
                Toast.makeText(this, response?.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
