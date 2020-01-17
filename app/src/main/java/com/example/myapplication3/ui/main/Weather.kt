package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Weather : Serializable {

    @SerializedName("temp")
    var temp: Float = 0.0F

    @SerializedName("pressure")
    var pressure : Int = 0

    @SerializedName("humidity")
    var humidity: Int = 0

}