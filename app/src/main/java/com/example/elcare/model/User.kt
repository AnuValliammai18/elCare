package com.example.elcare.model

data class Person(
    val age:Int=0,
    val contactAddress:String="",
    val contactPhone:Long=0,
    val contactname:String="",
    val email:String="",
    val name:String="")
data class HealthRecord(
    var bp:Int=0,
    var glucose:Int=0,
    var heartRate:Int=0)