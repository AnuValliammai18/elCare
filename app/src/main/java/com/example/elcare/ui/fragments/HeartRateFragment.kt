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
import kotlinx.android.synthetic.main.fragment_heart_rate.*

class HeartRateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_heart_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val healthDetailActivity = activity as HealthDetailActivity
        healthDetailActivity.viewModel._glucoseval.observe(viewLifecycleOwner, Observer {
            val lineDataSet = LineDataSet(it, "Heart Beat Rate")
            val dataset = arrayListOf<ILineDataSet>()
            dataset.add(lineDataSet)
            val data = LineData(dataset)
            heartRateChart.data = data
            heartRateChart.description.text = "Days"
            heartRateChart.invalidate()
        })
    }
}
