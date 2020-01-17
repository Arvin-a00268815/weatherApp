package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rain : Serializable {

    @SerializedName("3h")
    var h : Int = 0
}