package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Clouds : Serializable {

    @SerializedName("cloudiness")
    var cloudiness : Int = 0
}