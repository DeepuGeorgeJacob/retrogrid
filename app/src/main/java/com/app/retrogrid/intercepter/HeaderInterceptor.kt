package com.app.retrogrid.intercepter

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer your_token_here")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}