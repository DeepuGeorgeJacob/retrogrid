package com.app.retrogrid.network

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import com.app.retrogrid.configuration.BasePropertyConfiguration
import com.app.retrogrid.configuration.RetrogridConfiguration
import com.app.retrogrid.intercepter.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroGridNetworkClient {

    private fun dynamicRetrofit(baseUrl: String): Retrofit {
        val clientBuilder = OkHttpClient.Builder().addInterceptor(LoggingInterceptor())
        RetrogridConfiguration.getInterceptors().map {
            clientBuilder.addInterceptor(it)
        }
        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory())
            .client(client)
            .build()
    }


    fun <T> buildService(service: Class<T>): T {
        val baseUrl = service.getDeclaredAnnotation(
            RetrofitServiceConfiguration::class.java
        )?.baseUrl ?: BasePropertyConfiguration().getBaseUrl()
        ?: RetrogridConfiguration.getBaseUrl()
        return dynamicRetrofit(baseUrl).create(service)
    }

}