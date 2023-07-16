package com.app.retrogrid.configuration

import okhttp3.Interceptor
import kotlin.reflect.KClass

data class ServiceLayerConfiguration(
    val baseUrl: String? = null,
    val errorResponseClass: KClass<*>? = null,
    val interceptors: Array<KClass<out Interceptor>>? = emptyArray(),
    val isDefaultNetworkLogEnabled: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceLayerConfiguration

        if (baseUrl != other.baseUrl) return false
        if (errorResponseClass != other.errorResponseClass) return false
        if (interceptors != null) {
            if (other.interceptors == null) return false
            if (!interceptors.contentEquals(other.interceptors)) return false
        } else if (other.interceptors != null) return false
        if (isDefaultNetworkLogEnabled != other.isDefaultNetworkLogEnabled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = baseUrl?.hashCode() ?: 0
        result = 31 * result + (errorResponseClass?.hashCode() ?: 0)
        result = 31 * result + (interceptors?.contentHashCode() ?: 0)
        result = 31 * result + isDefaultNetworkLogEnabled.hashCode()
        return result
    }
}
