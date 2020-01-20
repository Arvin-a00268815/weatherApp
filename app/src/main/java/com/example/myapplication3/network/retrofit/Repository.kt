package com.example.myapplication3.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication3.StandardDeviationCalculator
import com.example.myapplication3.WeatherHelper
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class Repository private constructor(val context: Context, val apiCall: ApiCall) {


    companion object {

        fun getInstance(context: Context, apiCall: ApiCall) : Repository{
            return Repository(context, apiCall)
        }
    }


    fun getProgressState(): MutableLiveData<Int> {

        progress.value = View.GONE
        return progress
    }

    var progress = MutableLiveData<Int>()

    val cloudinessTemp = MutableLiveData<Int>()
    val currentWeatherLiveData = MutableLiveData<WeatherHelper>()

    fun currentWeather() = currentWeatherLiveData as LiveData<WeatherHelper>




    fun getCurrentWeather() : MutableLiveData<WeatherHelper> {

//        if (!isNetworkAvailable(context)){
//            throw NoInternetException()
//        }

        val weather = WeatherHelper()
        apiCall.getCurrentWeather().enqueue(object : Callback<WeatherHelper>{
            override fun onFailure(call: Call<WeatherHelper>, t: Throwable) {

                System.out.println("--Er-")
            }

            override fun onResponse(call: Call<WeatherHelper>, response: Response<WeatherHelper>) {



                System.out.println("--rp-")
                val temp = response.body()




                weather.coordinates = temp?.coordinates
                weather.name = temp?.name!!
                weather.rain = temp?.rain
                weather.wind = temp?.wind
                weather.weather = temp?.weather
                weather.clouds = temp?.clouds



                currentWeatherLiveData.postValue(weather)

                saveState?.onSaveWeather(weather)

                if (weather!!.clouds!!.cloudiness > 50){
                    cloudinessTemp.postValue(View.VISIBLE)

                }else{
                    cloudinessTemp.postValue(View.GONE)

                }


            }

        })

        return currentWeatherLiveData

    }

    interface SaveState{
        fun onSaveWeather(weatherHelper: WeatherHelper)
        fun onSaveSD(sd : Double)
    }

    var saveState : SaveState ? = null

    private fun isNetworkAvailable(context : Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    fun getSD() : MutableLiveData<Double> {

//        if(!isNetworkAvailable(context)){
//            throw NoInternetException()
//        }
        var liveData = MutableLiveData<Double>()




        val responseItems = ArrayList<Float>()

        progress.postValue(View.VISIBLE)



        Observable.range(1,5)
            .flatMap {
                apiCall.getWeatherForecast(it.toString()).subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<WeatherHelper>(){

                override fun onComplete() {
                    Log.e("complete","-")

                    val sd = StandardDeviationCalculator.calculate(responseItems)
                    liveData.postValue(sd)

                    progress.postValue(8)
                    saveState!!.onSaveSD(sd)


                }

                override fun onNext(t: WeatherHelper) {
                    Log.e("item", "="+t.weather!!.temp)
                    responseItems.add(t.weather!!.temp)
                }

                override fun onError(e: Throwable) {
                    Log.e("error","--")
                }

            })
        

        return liveData

    }



}