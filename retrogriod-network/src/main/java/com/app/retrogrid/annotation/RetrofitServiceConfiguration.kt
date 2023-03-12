package com.app.retrogrid.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class RetrofitServiceConfiguration(val baseUrl:String)
