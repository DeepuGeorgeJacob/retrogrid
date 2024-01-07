package com.app.retrogrid.extensions

import com.app.retrogrid.configuration.BasePropertyConfiguration
import com.app.retrogrid.configuration.RetrogridConfiguration
import com.app.retrogrid.configuration.ServiceLayerConfiguration
import com.app.retrogrid.intercepter.LoggingInterceptor
import okhttp3.Interceptor
import kotlin.reflect.full.createInstance

internal fun ServiceLayerConfiguration?.getBaseUrl(): String {
    return this?.baseUrl ?: RetrogridConfiguration.getBaseUrl()
    ?: BasePropertyConfiguration().getBaseUrl() ?: ""
}

internal fun ServiceLayerConfiguration?.getInterceptors(): List<Interceptor> {
    val interceptors = mutableListOf<Interceptor>()
    if (this?.isDefaultNetworkLogEnabled == true || RetrogridConfiguration.isLogEnabled()) {
        interceptors.add(LoggingInterceptor())
    }
    interceptors.addAll(
        this?.interceptors?.map { it.createInstance() } ?: RetrogridConfiguration.getInterceptors()
    )
    return interceptors
}