package com.example.myapplication3.network.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {

    companion object{

        val baseUrl = "https://twitter-code-challenge.s3.amazonaws.com"
        fun getInstance() : ApiCall{
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiCall::class.java)
        }
    }
}