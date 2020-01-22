package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Wind (

    @SerializedName("speed")
    var speed : Double,

    @SerializedName("degree")
    var degree : Int )
: Serializable