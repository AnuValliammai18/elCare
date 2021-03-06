package com.example.elcare.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.elcare.R
import com.example.elcare.SigninActivity
import com.example.elcare.model.Person
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_user.*

class NewUserActivity : AppCompatActivity() {

    var imageUri: Uri? = null
    var storageReference = FirebaseStorage.getInstance().getReference("uploads")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        supportActionBar?.title = "New User"

        imageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 101)
        }

        create.setOnClickListener {
            if (name.text != null && age.text != null && contactName.text != null && contactPhone.text != null) {
                create.isEnabled = false
                progressBar3.visibility = View.VISIBLE
                nameLayout.isEnabled = false
                ageLayout.isEnabled = false
                contactnameLayout.isEnabled = false
                contactphoneLayout.isEnabled = false
                contactaddressLayout.isEnabled = false
                emailLayout.isEnabled = false
                uploadData()
            } else
                Toast.makeText(this, "Fill all Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData() {
        imageUri?.let {
            val uid = FirebaseAuth.getInstance().uid!!
            val fileStorage = storageReference.child(uid)
                .child("${System.currentTimeMillis()}.${getFileExtension(it)}")
            fileStorage.putFile(it).addOnSuccessListener {
                fileStorage.downloadUrl.addOnSuccessListener { downloadUri ->
                    val picUrl = downloadUri.toString()
                    val name = name.text.toString()
                    val age = age.text.toString()
                    val contactName = contactName.text.toString()
                    val contactPhone = contactPhone.text.toString()
                    val contactAddress = contactAddress.text.toString()
                    val email = email.text.toString()
                    val user = Person(
                        age.toInt(),
                        contactAddress,
                        contactPhone.toLong(),
                        contactName,
                        email,
                        name,
                        picUrl
                    )
                    val databaseReference = FirebaseDatabase.getInstance().reference

                    databaseReference.child(uid).setValue(user).addOnSuccessListener {
                        Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show()
                        startActivityForResult(Intent(this, MonitorActivity::class.java), 200)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(this).load(imageUri).into(imageView)
        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        val signoutBtn = (menu?.findItem(R.id.app_signout_button)?.actionView as ImageButton).also {
            it.setBackgroundResource(R.drawable.power_off_foreground)
        }
        signoutBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle("Logout")
                setMessage("Are you sure you want to logout?")
                setCancelable(false)
                setPositiveButton(
                    "Logout"
                ) { _, _ ->
                    AuthUI.getInstance().signOut(this@NewUserActivity)
                        .addOnCompleteListener {
                            startActivity(Intent(this@NewUserActivity, SigninActivity::class.java))
                            this@NewUserActivity.finish()
                        }
                }
                setNegativeButton("No") { _, _ ->
                }
            }
            builder.show()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
