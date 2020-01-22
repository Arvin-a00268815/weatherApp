package com.example.myapplication3

import com.example.myapplication3.ui.main.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherHelper(


    @SerializedName("name")
    var name : String,

    @SerializedName("coord")
    var coordinates : Coordinates,

    @SerializedName("weather")
    var weather : Weather,

    @SerializedName("wind")
    var wind : Wind,

    @SerializedName("rain")
    var rain : Rain,

    @SerializedName("clouds")
    var clouds : Clouds


) : Serializable