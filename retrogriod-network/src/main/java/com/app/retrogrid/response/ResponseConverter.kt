package com.app.retrogrid.response

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import kotlin.reflect.KClass

fun <R> Response<R>.toCompletedResult(errorClass: KClass<*>): RetrogridResponse<R> {
    return if (isSuccessful) {
        RetrogridResponse.Success(body()) as RetrogridResponse<R>
    } else {
        val gson = Gson()
        val errorResponse = gson.fromJson(errorBody()?.string() ?: "{}", errorClass.java)
        RetrogridResponse.Failure(errorResponse)
    }
}

fun <R> Throwable.toErrorResult(): RetrogridResponse<R> {
    return when (this) {
        is IOException -> RetrogridResponse.NetworkError("Unable to reach host : ${this.localizedMessage}")
        else -> RetrogridResponse.UnknownError(errorMessage = "Unknown error occurred : ${this.localizedMessage}")
    }
}