package com.app.retrogrid.configuration

import com.app.retrogrid.mock.MockProvider
import okhttp3.Interceptor

object RetrogridConfiguration {
    private var isLoggEnabled = false
    private var errorKlass: Any? = null
    private val interceptors = mutableListOf<Interceptor>()
    private var baseUrl: String? = null

    private var mockProvider:MockProvider? = null

    fun enableLog(isLoggEnabled: Boolean): RetrogridConfiguration {
        this.isLoggEnabled = isLoggEnabled
        return this
    }

    fun <T> setErrorResponseClass(errorKlass: T): RetrogridConfiguration {
        this.errorKlass = errorKlass
        return this
    }

    fun <T : Interceptor> addInterceptor(interceptor: T): RetrogridConfiguration {
        interceptors.add(interceptor)
        return this
    }

    fun setBaseUrl(url: String): RetrogridConfiguration {
        this.baseUrl = url
        return this
    }

    fun setMockProvider(mockProvider: MockProvider) {
        this.mockProvider = mockProvider
    }


    internal fun isLogEnabled() = this.isLoggEnabled

    internal fun getErrorClass() = this.errorKlass

    internal fun getInterceptors() = this.interceptors

    internal fun getBaseUrl() = this.baseUrl

    internal fun getMockProvider() = this.mockProvider


}



