package com.example.elcare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elcare.model.HealthRecord
import com.github.mikephil.charting.data.Entry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HeathDetailViewModel : ViewModel() {

    var _healthrecord = MutableLiveData<HealthRecord>()
    val _glucoseval = MutableLiveData<ArrayList<Entry>>()
    val _bpval = MutableLiveData<ArrayList<Entry>>()
    val _heartRateval = MutableLiveData<ArrayList<Entry>>()
    val uid = FirebaseAuth.getInstance().uid!!

    fun getCurrentHealthStatus() {
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(uid)
        val personListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val health = p0.child(uid).child("HealthRecord").children.last()
                val h = health.getValue(HealthRecord::class.java)
                _healthrecord.value = h
            }
        }
        databaseReference.addValueEventListener(personListener)

    }

    fun getHealthRecord() {
        val glucoseval = arrayListOf<Entry>()
        val bpval = arrayListOf<Entry>()
        val heartRateval = arrayListOf<Entry>()
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child(uid).child("HealthRecord")
        val healthRecordListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var c = 1
                for (health in p0.children) {
                    val h = health.getValue(HealthRecord::class.java)
                    h?.let {
                        glucoseval.add(Entry(c++.toFloat(), it.glucose.toFloat()))
                        bpval.add(Entry(c++.toFloat(), it.bp.toFloat()))
                        heartRateval.add(Entry(c++.toFloat(), it.heartRate.toFloat()))
                    }
                }
                _glucoseval.value = glucoseval
                _bpval.value = bpval
                _heartRateval.value = heartRateval
            }
        }
        databaseReference.addValueEventListener(healthRecordListener)
    }
}