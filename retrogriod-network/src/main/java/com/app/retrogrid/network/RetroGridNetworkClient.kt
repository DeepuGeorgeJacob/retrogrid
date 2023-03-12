package com.app.retrogrid.network

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroGridNetworkClient {
    private val lazyRetrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory())
            .build()
    }

    private fun dynamicRetrofit(baseUrl:String):Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory())
            .build()
    }



    fun <T>buildService(service:Class<T>):T {
        val retrofitConfig = service.getDeclaredAnnotation(RetrofitServiceConfiguration::class.java)
        return if (retrofitConfig!=null) {
            dynamicRetrofit(retrofitConfig.baseUrl).create(service)
        } else {
            lazyRetrofit.create(service)
        }
    }

}