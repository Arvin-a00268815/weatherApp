package com.example.myapplication3

import com.example.myapplication3.ui.main.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherHelper : Serializable{


    @SerializedName("name")
    var name : String ?= ""

    @SerializedName("coord")
    var coordinates : Coordinates ?= null

    @SerializedName("weather")
    var weather : Weather ? = null

    @SerializedName("wind")
    var wind : Wind ? = null

    @SerializedName("rain")
    var rain : Rain ? = null


    @SerializedName("clouds")
    var clouds : Clouds?= null



}