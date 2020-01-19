package com.example.myapplication3

import android.content.Context
import com.example.myapplication3.network.retrofit.ApiBuilder
import com.example.myapplication3.network.retrofit.ApiCall
import com.example.myapplication3.network.retrofit.Repository
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.Calls


@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {


    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var apiCall: ApiCall


    @Before
    fun init(){

        repository = Repository.getInstance(context, apiCall)
    }


    @Test
    fun `test getCurrentWeather method`(){


        val rawResponse = "{\"coord\":{\"lon\":-122.42,\"lat\":37.77},\"weather\":{\"temp\":14.77,\"pressure\":1007,\"humidity\":85},\"wind\":{\"speed\":0.51,\"deg\":284},\"rain\":{\"3h\":1},\"clouds\":{\"cloudiness\":65},\"name\":\"San Francisco\"}"
        val gson = Gson()
        val weatherHelper = gson.fromJson(rawResponse, WeatherHelper::class.java)
        val response : Response<WeatherHelper> = Response.success(weatherHelper)

        Mockito.`when`(apiCall.getCurrentWeather()).thenReturn(Calls.response(response))

        repository.getCurrentWeather()

        Mockito.verify(apiCall, Mockito.times(1)).getCurrentWeather()

    }


    lateinit var testObservable : TestObserver<WeatherHelper>

    @Test
    fun `test getSD method`() {

        val rawResponse = "{\"coord\":{\"lon\":-122.42,\"lat\":37.77},\"weather\":{\"temp\":14.77,\"pressure\":1007,\"humidity\":85},\"wind\":{\"speed\":0.51,\"deg\":284},\"rain\":{\"3h\":1},\"clouds\":{\"cloudiness\":65},\"name\":\"San Francisco\"}"
        val gson = Gson()
        val weatherHelper = gson.fromJson(rawResponse, WeatherHelper::class.java)
        val response : Response<WeatherHelper> = Response.success(weatherHelper)


        Mockito.`when`(apiCall.getWeatherForecast(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(weatherHelper))

        repository.getSD()

        Mockito.verify(apiCall, Mockito.times(5)).getWeatherForecast(ArgumentMatchers.anyString())

        testObservable = TestObserver()
        apiCall.getWeatherForecast(ArgumentMatchers.anyString()).subscribe(testObservable)

        testObservable.assertComplete()
    }
}
