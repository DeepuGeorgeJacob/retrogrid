package com.app.retrogrid.intercepter

import com.app.retrogrid.configuration.RetrogridConfiguration
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.Http2Reader.Companion.logger
import java.util.*


internal class LoggingInterceptor : Interceptor {

    private val gson = GsonBuilder().setPrettyPrinting().create()


    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        logger.info("Request url ${request.url}")
        logger.info("Header url ${request.headers}")

        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )


        val t2 = System.nanoTime()
        logger.info(
            String.format(
                Locale.getDefault(),
                "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )

        val content = response.body?.string() ?: "{}"
        val contentType: MediaType? = response.body?.contentType()

        logger.info(toPrettyFormat(content))


        val wrappedBody: ResponseBody = content.toResponseBody(contentType)
        return response.newBuilder().body(wrappedBody).build()

    }

    private fun toPrettyFormat(jsonString: String?): String? {
        val parser = JsonParser()
        try {
            val json = parser.parse(jsonString).asJsonObject
            return gson.toJson(json)
        } catch (exception: Exception) {
            logger.info(exception.stackTraceToString())
        }
        return "{}"
    }
}