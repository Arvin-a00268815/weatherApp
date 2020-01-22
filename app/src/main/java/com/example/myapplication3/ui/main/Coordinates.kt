package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Coordinates(

    @SerializedName("lat")
    var lat : String,

    @SerializedName("lon")
    var long : String
    ) : Serializable