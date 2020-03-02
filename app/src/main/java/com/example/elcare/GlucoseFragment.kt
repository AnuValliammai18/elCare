package com.example.elcare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_glucose.*


class GlucoseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_glucose, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val healthDetailActivity = activity as HealthDetailActivity
        val uid: String = healthDetailActivity.uid
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child(uid).child("HealthRecord")
        val glucoseval = arrayListOf<Entry>()
        val healthRecordListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var c = 1
                for (health in p0.children) {
                    val h = health.getValue(HealthRecord::class.java)
                    h?.let {
                        glucoseval.add(Entry(c++.toFloat(), it.glucose.toFloat()))
                    }
                }
            }
        }
        databaseReference.addValueEventListener(healthRecordListener)
        display.setOnClickListener {
            val lineDataSet = LineDataSet(glucoseval, "Glucose Level")
            val dataset = arrayListOf<ILineDataSet>()
            dataset.add(lineDataSet)
            val data = LineData(dataset)
            glucoseChart.data = data
            glucoseChart.description.text = "Days"
        }
    }
}
