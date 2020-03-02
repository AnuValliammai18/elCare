package com.example.elcare.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.elcare.ui.fragments.BPFragment
import com.example.elcare.ui.fragments.GlucoseFragment
import com.example.elcare.ui.fragments.HeartRateFragment

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