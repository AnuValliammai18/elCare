package com.example.elcare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elcare.model.Tip

class TipsViewModel : ViewModel() {
    private val _tips = MutableLiveData<MutableList<Tip>>()
    val tip: LiveData<MutableList<Tip>>
        get() {
            return _tips
        }

    fun getTips() {
        val tip = mutableListOf(
            Tip("1. Get active"),
            Tip("Take supplements as necessary"),
            Tip("Eat a healthy diet"),
            Tip("Base your diet on plenty of foods rich in carbohydrates"),
            Tip("Replace saturated with unsaturated fat"),
            Tip("Enjoy plenty of fruits and vegetables"),
            Tip("Reduce salt and sugar intake"),
            Tip("Eat regularly, control the portion size"),
            Tip("Drink plenty of fluids"),
            Tip("Maintain a healthy body weight"),
            Tip("Get on the move, make it a habit!"),
            Tip("Wash your hands frequently"),
            Tip("Learn how to manage stress"),
            Tip("Get plenty of rest"),
            Tip("Take steps to prevent infections"),
            Tip("Schedule annual physicals"),
            Tip("Avoid contact with people who are sick")
        )
        _tips.value = tip
    }
}