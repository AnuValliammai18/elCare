package com.example.elcare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.elcare.R
import com.example.elcare.model.HealthRecord
import com.example.elcare.ui.HealthDetailActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_b_p.*

class BPFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val healthDetailActivity = activity as HealthDetailActivity
        val uid: String = healthDetailActivity.uid
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child(uid).child("HealthRecord")
        val bpval = arrayListOf<Entry>()
        val healthRecordListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var c = 1
                for (health in p0.children) {
                    val h = health.getValue(HealthRecord::class.java)
                    h?.let {
                        bpval.add(Entry(c++.toFloat(), it.bp.toFloat()))
                    }
                }
                val lineDataSet = LineDataSet(bpval, "Blood Pressure Level")
                val dataset = arrayListOf<ILineDataSet>()
                dataset.add(lineDataSet)
                val data = LineData(dataset)
                bpchart.data = data
                bpchart.description.text = "Days"
                bpchart.invalidate()
            }
        }
        databaseReference.addValueEventListener(healthRecordListener)
    }
}
