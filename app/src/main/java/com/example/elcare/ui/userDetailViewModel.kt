package com.example.elcare.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elcare.model.Person
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDetailViewModel : ViewModel() {

    var _person = MutableLiveData<Person>()
    val uid = FirebaseAuth.getInstance().uid!!

    fun getPerson() {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val personListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                val person = p0.child(uid).getValue(Person::class.java)
                _person.value = person
            }
        }
        databaseReference.addValueEventListener(personListener)
    }

}