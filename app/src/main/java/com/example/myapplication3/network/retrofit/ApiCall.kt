package com.example.myapplication3.network.retrofit

import com.example.myapplication3.WeatherHelper
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCall {


    @GET("/current.json")
    fun getCurrentWeather() : Call<WeatherHelper>

    @GET("/future_{id}.json")
    fun getWeatherForecast(@Path("id") id : String) : Observable<WeatherHelper>
}