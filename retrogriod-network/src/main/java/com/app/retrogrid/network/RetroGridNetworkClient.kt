package com.app.retrogrid.network

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import com.app.retrogrid.configuration.ServiceLayerConfiguration
import com.app.retrogrid.exytensions.getBaseUrl
import com.app.retrogrid.exytensions.getInterceptors
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroGridNetworkClient {

    private fun dynamicRetrofit(serviceLayerConfiguration: ServiceLayerConfiguration?): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        serviceLayerConfiguration.getInterceptors().forEach {
            clientBuilder.addInterceptor(it)
        }
        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(serviceLayerConfiguration.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrogridCallAdapterFactory(serviceLayerConfiguration))
            .client(client)
            .build()
    }


    fun <T> buildService(service: Class<T>): T {
        val serviceLayerConfiguration = getOrNullErrorServiceLayerData(service)
        return dynamicRetrofit(serviceLayerConfiguration).create(service)
    }

    private fun <T> getOrNullErrorServiceLayerData(service: Class<T>): ServiceLayerConfiguration? {
        val annotation = service.getDeclaredAnnotation(RetrofitServiceConfiguration::class.java)
        if (annotation != null) {
            return ServiceLayerConfiguration(
                baseUrl = annotation.baseUrl,
                errorResponseClass = annotation.errorResponseClass,
                interceptors = annotation.interceptors,
                isDefaultNetworkLogEnabled = annotation.enableRequestResponseDefaultLog
            )
        }
        return null
    }

}