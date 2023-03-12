package com.app.retrogrid.network

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import com.app.retrogrid.configuration.BaseConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroGridNetworkClient {

    private fun dynamicRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory())
            .build()
    }


    fun <T> buildService(service: Class<T>): T {
        val baseUrl = BaseConfiguration().getBaseUrl() ?: service.getDeclaredAnnotation(
            RetrofitServiceConfiguration::class.java
        )?.baseUrl.orEmpty()
        return dynamicRetrofit(baseUrl).create(service)
    }

}