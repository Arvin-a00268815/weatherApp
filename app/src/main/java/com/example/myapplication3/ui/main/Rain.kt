package com.example.myapplication3.ui.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rain(
    @SerializedName("3h")
    var h : Int
): Serializable