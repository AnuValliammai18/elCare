package com.example.elcare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.elcare.R
import com.example.elcare.adapters.RecyclerViewAdapter
import com.example.elcare.viewmodel.TipsViewModel
import kotlinx.android.synthetic.main.fragment_tips.*

class TipsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tips, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(TipsViewModel::class.java)
        viewModel.getTips()
        val viewAdapter = RecyclerViewAdapter()
        recyclerView.apply {
            adapter = viewAdapter
        }
        viewModel.tip.observe(viewLifecycleOwner, Observer {
            viewAdapter.myDataSet = it
        })
    }

}
