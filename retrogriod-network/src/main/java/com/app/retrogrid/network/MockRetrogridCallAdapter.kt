package com.app.retrogrid.network

import com.app.retrogrid.response.RetrogridResponse
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type


class MockRetrogridCallAdapter<R>(
    private val responseType: Type,
    private val baseUrl: HttpUrl
) : CallAdapter<R, MockRetrogridCall<R>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): MockRetrogridCall<R> {
        val fullUrl = (call.request() as Request).url.toString()
        val jsonName = "${getRelativePath(baseUrl.toString(), fullUrl)}.json"
        val inputStream =
            Thread.currentThread().contextClassLoader?.getResourceAsStream(jsonName)
        return MockRetrogridCall(call, responseType,readJsonFile(inputStream))
    }

    private fun getRelativePath(baseUrl: String, fullUrl: String): String {
        if (fullUrl.startsWith(baseUrl)) {
            return fullUrl.removePrefix(baseUrl)
        } else {
            throw IllegalArgumentException("Invalid URLs: $baseUrl and $fullUrl")
        }
    }

    private fun readJsonFile(inputStream: InputStream?): String {
        val br = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }

}

class MockRetrogridCall<R>(
    private val delegate: Call<R>,
    private val responseType: Type,
    private val readJsonFile: String,
) : Call<RetrogridResponse<R>> {
    override fun enqueue(callback: Callback<RetrogridResponse<R>>) {

        println("RESPONSE TYPE $responseType")
        val gson = Gson()
        val body: Any = gson.fromJson(readJsonFile, responseType
        )

        val response = RetrogridResponse.Success(body) as RetrogridResponse<R>
        callback.onResponse(this, Response.success(response))
    }

    override fun clone(): Call<RetrogridResponse<R>> = MockRetrogridCall(
        delegate.clone(), responseType, readJsonFile
    )

    override fun execute(): Response<RetrogridResponse<R>> =
        Response.success(RetrogridResponse.UnknownError("An Unknown error occurred"))

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
