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





    fun getCurrentWeather() : MutableLiveData<WeatherHelper> {

//        if (!isNetworkAvailable(context)){
//            throw NoInternetException()
//        }

        val currentWeatherLiveData = MutableLiveData<WeatherHelper>()

        apiCall.getCurrentWeather().enqueue(object : Callback<WeatherHelper>{
            override fun onFailure(call: Call<WeatherHelper>, t: Throwable) {

                System.out.println("--Er-")
            }

            override fun onResponse(call: Call<WeatherHelper>, response: Response<WeatherHelper>) {



                System.out.println("--rp-")
                val temp = response.body()!!

                //temp!!.clouds.cloudiness = 20

                val weather = WeatherHelper(temp.name,
                    temp.coordinates,
                    temp.weather,
                    temp.wind,
                    temp.rain,
                    temp.clouds)


                currentWeatherLiveData.postValue(weather)

                saveState?.onSaveWeather(weather)




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


    fun getSD(repo: Repo){

//        if(!isNetworkAvailable(context)){
//            throw NoInternetException()
//        }
       // val liveData = MutableLiveData<Double>()




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
                    //liveData.postValue(sd)

                    repo.onSDcomplete(sd)
                    progress.postValue(8)
                    saveState!!.onSaveSD(sd)


                }

                override fun onNext(t: WeatherHelper) {
                    Log.e("item", "="+t.weather.temp)
                    responseItems.add(t.weather.temp)
                }

                override fun onError(e: Throwable) {
                    Log.e("error","--")
                }

            })

    }


    interface Repo{
        fun onSDcomplete(sd : Double)
    }


}