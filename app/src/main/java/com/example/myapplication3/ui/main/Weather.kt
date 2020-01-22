package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Weather(

    @SerializedName("temp")
    var temp: Float,

    @SerializedName("pressure")
    var pressure : Int,

    @SerializedName("humidity")
    var humidity: Int )
: Serializable