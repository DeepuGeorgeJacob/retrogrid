package com.app.retrogrid.network

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import com.app.retrogrid.configuration.ServiceLayerConfiguration
import com.app.retrogrid.configuration.getMockDataObject
import com.app.retrogrid.extensions.getBaseUrl
import com.app.retrogrid.extensions.getInterceptors
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
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
            .addCallAdapterFactory(
                initCallAdapterFactory(serviceLayerConfiguration)
            )
            .client(client)
            .build()
    }

    private fun initCallAdapterFactory(serviceLayerConfiguration: ServiceLayerConfiguration?): CallAdapter.Factory {
        val mockDataObject = serviceLayerConfiguration?.getMockDataObject()
        return if (mockDataObject == null || !mockDataObject.isEnabled) {
            RetrogridCallAdapterFactory(serviceLayerConfiguration)
        } else {
            MockRetrogridCallAdapterFactory()
        }
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
                isDefaultNetworkLogEnabled = annotation.enableRequestResponseDefaultLog,
                mockProvider = annotation.mockDataProvider
            )
        }
        return null
    }

}