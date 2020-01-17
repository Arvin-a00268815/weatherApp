package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Coordinates : Serializable{

    @SerializedName("lat")
    var lat = "";

    @SerializedName("lon")
    var long = "";
}