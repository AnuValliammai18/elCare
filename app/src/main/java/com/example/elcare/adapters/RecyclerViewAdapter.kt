package com.example.elcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.elcare.R
import com.example.elcare.databinding.CustomRowLayoutBinding
import com.example.elcare.model.Tip


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var myDataSet = listOf<Tip>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val customRowLayoutBinding: CustomRowLayoutBinding) :
        RecyclerView.ViewHolder(customRowLayoutBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.custom_row_layout,
                parent,
                false
            )
        )

    override fun getItemCount() = myDataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customRowLayoutBinding.tips = myDataSet[position]
    }
}