package com.app.retrogrid.response


sealed class RetrogridResponse<T> {
    data class Success<T>(val responseBody: T) : RetrogridResponse<T>()
    data class Failure<T>(val errorResponseBody: Any) : RetrogridResponse<T>()
    data class NetworkError<T>(val errorMessage: String) : RetrogridResponse<T>()
    data class UnknownError<T>(val errorMessage: String) : RetrogridResponse<T>()
}
