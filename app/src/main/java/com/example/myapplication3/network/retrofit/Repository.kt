package com.example.myapplication3.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.myapplication3.StandardDeviationCalculator
import com.example.myapplication3.WeatherHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
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


    fun isNetExist(): MutableLiveData<Boolean> {

        isNetAvailable.value = true
        return isNetAvailable
    }

    var isNetAvailable = MutableLiveData<Boolean>()


    fun getProgressState(): MutableLiveData<Int> {

        progress.value = View.GONE
        return progress
    }

    var progress = MutableLiveData<Int>()

    val cloudinessTemp = MutableLiveData<Int>()




    fun getCurrentWeather() : MutableLiveData<WeatherHelper> {

        if (!isNetworkAvailable(context)){
            throw NoInternetException()
        }

        val weather = WeatherHelper()
        var liveData = MutableLiveData<WeatherHelper>()
        apiCall.getCurrentWeather().enqueue(object : Callback<WeatherHelper>{
            override fun onFailure(call: Call<WeatherHelper>, t: Throwable) {

                System.out.println("--Er-")
            }

            override fun onResponse(call: Call<WeatherHelper>, response: Response<WeatherHelper>) {



                System.out.println("--rp-")
                val temp = response.body()




                weather.coordinates = temp?.coordinates
                weather.name = temp?.name
                weather.rain = temp?.rain
                weather.wind = temp?.wind
                weather.weather = temp?.weather
                weather.clouds = temp?.clouds



                liveData.postValue(weather)

                saveState?.onSaveWeather(weather)

                if (weather!!.clouds!!.cloudiness > 50){
                    cloudinessTemp.postValue(View.VISIBLE)

                }else{
                    cloudinessTemp.postValue(View.GONE)

                }


            }

        })

        return liveData

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

        if(!isNetworkAvailable(context)){
            throw NoInternetException()
        }
        var liveData = MutableLiveData<Double>()


        val o1 = apiCall.getWeatherForecast("1")
        val o2 = apiCall.getWeatherForecast("2")
        val o3 = apiCall.getWeatherForecast("3")
        val o4 = apiCall.getWeatherForecast("4")
        val o5 = apiCall.getWeatherForecast("5")

        val list = ArrayList<Observable<WeatherHelper>>()
        list.add(o1)
        list.add(o2)
        list.add(o3)
        list.add(o4)
        list.add(o5)


        val responseItems = ArrayList<Float>()

        progress.postValue(View.VISIBLE)

        Observable.merge(listOf(o1, o2, o3, o4, o5))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<WeatherHelper>(){

                override fun onComplete() {


                    val sd = StandardDeviationCalculator.calculate(responseItems)
                    liveData.postValue(sd)

                    progress.postValue(8)
                    saveState!!.onSaveSD(sd)


                }

                override fun onNext(t: WeatherHelper) {
                    responseItems.add(t.weather!!.temp)
                }

                override fun onError(e: Throwable) {
                }

            })

        return liveData

    }


}