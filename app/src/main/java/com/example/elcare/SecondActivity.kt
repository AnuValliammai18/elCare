package com.example.elcare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val bp=intent.getIntExtra("bp",0)
        val glucose=intent.getIntExtra("glucose",0)
        val heartrate=intent.getIntExtra("heartrate",0)
        val LineDataSet= LineDataSet(heartRateValues(),"Heart Beat Rate")
        val dataset= arrayListOf<ILineDataSet>()
        dataset.add(LineDataSet)
        val data=LineData(dataset)
        heartRateChart.data=data
        heartRateChart.description.text="Days"
        glucoseLevel.progress=glucose.toFloat()
        bpLevel.progress=bp.toFloat()
        Log.i("database",bp.toString())
    }
    private fun heartRateValues():ArrayList<Entry>{
        val heartRateval= arrayListOf<Entry>(Entry(1f,60f),Entry(2f,50f),Entry(3f,70f),Entry(4f,60f),Entry(5f,80f))
        return heartRateval
    }

}
