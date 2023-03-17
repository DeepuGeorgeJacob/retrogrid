package com.app.retrogrid.configuration

import java.util.Properties

private const val BASE_URL_KEY = "api.base.url"
private const val PRO_FILE_NAME = "retrofit.properties"

internal class BasePropertyConfiguration {

    private val properties: Properties by lazy {
        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream = classLoader?.getResourceAsStream(PRO_FILE_NAME)
        val properties = Properties()
        properties.load(inputStream)
        properties
    }


    fun getBaseUrl(): String? {
        return properties.getProperty(BASE_URL_KEY, null)
    }
}