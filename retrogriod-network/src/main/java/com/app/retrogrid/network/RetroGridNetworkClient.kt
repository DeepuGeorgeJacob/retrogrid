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
        val baseUrl = service.getDeclaredAnnotation(
            RetrofitServiceConfiguration::class.java
        )?.baseUrl ?: BaseConfiguration().getBaseUrl().orEmpty()
        return dynamicRetrofit(baseUrl).create(service)
    }

}