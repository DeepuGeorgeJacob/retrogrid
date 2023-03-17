package com.app.retrogrid.network

import com.app.retrogrid.annotation.ErrorResponseMap
import com.app.retrogrid.configuration.RetrogridConfiguration
import com.app.retrogrid.response.RetrogridResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

internal class RetrogridCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized"
        }
        val annotationClass =
            annotations.firstOrNull { it.annotationClass == ErrorResponseMap::class } as? ErrorResponseMap
        val errorClass = annotationClass?.errorClass ?: RetrogridConfiguration.getErrorClass() as? KClass<*>
        ?: throw NullPointerException("Error class should be defined")


        val successResponseType = getParameterUpperBound(0, returnType)
        // if the response type is not RetrogridResponse then we can't handle this type, so we return null
        if (getRawType(successResponseType) != RetrogridResponse::class.java) {
            return null
        }

        // the response type should be parameterized
        check(successResponseType is ParameterizedType) { "Response must be parameterized " }

        val successBodyType = getParameterUpperBound(0, successResponseType)

        return RetrogridCallAdapter<Any>(successBodyType, errorClass)
    }
}