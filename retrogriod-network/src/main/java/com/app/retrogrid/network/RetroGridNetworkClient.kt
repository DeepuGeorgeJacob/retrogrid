package com.app.retrogrid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroGridNetworkClient {
    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory())
            .build()
    }

    fun <T>buildService(service:Class<T>):T {
        return retrofit.create(service)
    }

}