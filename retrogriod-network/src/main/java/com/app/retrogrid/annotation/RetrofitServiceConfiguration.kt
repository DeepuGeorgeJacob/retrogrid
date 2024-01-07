package com.app.retrogrid.annotation

import com.app.retrogrid.mock.MockProvider
import okhttp3.Interceptor
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class RetrofitServiceConfiguration(
    val baseUrl: String,
    val errorResponseClass: KClass<*> = Nothing::class,
    val interceptors:Array<KClass<out Interceptor>> = [],
    val enableRequestResponseDefaultLog:Boolean = false,
    val mockDataProvider:KClass<out MockProvider> = Nothing::class
)
