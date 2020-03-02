package com.example.elcare

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        Log.i("tab", "created fragment")
        return when (position) {
            0 -> HeartRateFragment()
            1 -> BPFragment()
            else -> GlucoseFragment()
        }
    }
}