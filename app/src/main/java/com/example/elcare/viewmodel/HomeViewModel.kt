package com.example.elcare.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    val user = FirebaseAuth.getInstance().currentUser!!
    val _name = MutableLiveData<String>()
    val _email = MutableLiveData<String>()
    val _photoUrl = MutableLiveData<Uri>()

    fun getDetail() {
        val name = user.displayName ?: "elCare"
        val email = user.email ?: "elCare@gmail.com"
        val photoUrl = user.photoUrl!!
        _name.value = name
        _email.value = email
        _photoUrl.value = photoUrl
    }

}