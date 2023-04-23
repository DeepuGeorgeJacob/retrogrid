package com.app.retrogrid.configuration

import okhttp3.Interceptor
import kotlin.reflect.KClass

data class ServiceLayerConfiguration(
    val baseUrl: String? = null,
    val errorResponseClass: KClass<*>? = null,
    val interceptors: Array<KClass<out Interceptor>>? = emptyArray(),
    val isDefaultNetworkLogEnabled: Boolean = false
)
