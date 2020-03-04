package com.example.elcare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.elcare.R
import com.example.elcare.ui.HealthDetailActivity
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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
        healthDetailActivity.viewModel._glucoseval.observe(viewLifecycleOwner, Observer {
            val lineDataSet = LineDataSet(it, "Blood Pressure Level")
            val dataset = arrayListOf<ILineDataSet>()
            dataset.add(lineDataSet)
            val data = LineData(dataset)
            bpchart.data = data
            bpchart.description.text = "Days"
            bpchart.invalidate()
        })
    }
}
