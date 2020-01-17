package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Wind : Serializable{

    @SerializedName("speed")
    var speed : Double = 0.0

    @SerializedName("degree")
    var degree : Int = 0
}