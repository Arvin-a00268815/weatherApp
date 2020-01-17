package com.example.myapplication3.ui.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication3.WeatherHelper
import com.example.myapplication3.network.retrofit.NoInternetException
import com.example.myapplication3.network.retrofit.Repository
import java.lang.Exception


class MainViewModel(application: Application, var savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    var repository = Repository(application.applicationContext)
    private var weatherHelper : MutableLiveData<WeatherHelper> = MutableLiveData()

    private var sdValue : MutableLiveData<Double> = MutableLiveData()
    private var checkInternet : MutableLiveData<Boolean> = MutableLiveData()

    var progress = repository.getProgressState()

    var cloudiness = repository.cloudinessTemp


    init {
        repository.saveState = object : Repository.SaveState{
            override fun onSaveWeather(weatherHelper: WeatherHelper) {
                saveWeather(weatherHelper)
            }

            override fun onSaveSD(sd: Double) {
                saveSD(sd)
            }
        }
    }


    fun onNoInternet() : LiveData<Boolean> {

        return checkInternet
    }
    fun getWeatherDetails() : LiveData<WeatherHelper>{


        weatherHelper.value?.let {

            return weatherHelper
        }

        if(savedStateHandle.contains("weather")) {
            weatherHelper.postValue(savedStateHandle.get<WeatherHelper>("weather"))

        }else {

            try {

                weatherHelper = repository.getCurrentWeather()
            }catch (e: NoInternetException){
                e.printStackTrace()
                checkInternet.postValue(true)
            }

        }

        return weatherHelper

    }

    fun saveWeather(weatherHelper: WeatherHelper){

        savedStateHandle.set("weather", weatherHelper)
    }

    fun saveSD(sdValue : Double){
        savedStateHandle.set("sd",sdValue)
    }


    fun getStandardDev() : LiveData<Double>{

        sdValue.value?.let {


            return sdValue

        }

        if(savedStateHandle.contains("sd")){
            sdValue.postValue(savedStateHandle.get<Double>("sd"))

        }else{
            try{
            sdValue = repository.getSD()
            }catch (e: NoInternetException){
                e.printStackTrace()
                checkInternet.postValue(true)
            }
        }


        return sdValue
    }


}
