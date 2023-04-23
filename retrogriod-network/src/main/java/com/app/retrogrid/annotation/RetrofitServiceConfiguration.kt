package com.app.retrogrid.annotation

import okhttp3.Interceptor
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class RetrofitServiceConfiguration(
    val baseUrl: String,
    val errorResponseClass: KClass<*> = Nothing::class,
    val interceptors:Array<KClass<out Interceptor>> = [],
    val enableRequestResponseDefaultLog:Boolean = false
)
