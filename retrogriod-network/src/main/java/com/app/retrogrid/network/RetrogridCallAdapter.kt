package com.app.retrogrid.network

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import kotlin.reflect.KClass

class RetrogridCallAdapter<R>(
    private val successType: Type,
    private val errorClassName: KClass<*>
) : CallAdapter<R, RetrogridCall<R>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): RetrogridCall<R> {
        return RetrogridCall(call, errorClassName)
    }
}