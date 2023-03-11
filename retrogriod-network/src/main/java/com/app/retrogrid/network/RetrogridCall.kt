package com.app.retrogrid.network

import com.app.retrogrid.response.RetrogridResponse
import com.app.retrogrid.response.toCompletedResult
import com.app.retrogrid.response.toErrorResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KClass

class RetrogridCall<R>(
    private val delegate: Call<R>,
    private val errorClassName: KClass<*>
) : Call<RetrogridResponse<R>> {

    override fun enqueue(callback: Callback<RetrogridResponse<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(
                    this@RetrogridCall,
                    Response.success(response.toCompletedResult(errorClassName))
                )
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                callback.onResponse(
                    this@RetrogridCall, Response.success(
                        throwable.toErrorResult()
                    )
                )
            }

        })
    }

    override fun clone(): Call<RetrogridResponse<R>> = RetrogridCall(
        delegate.clone(),
        errorClassName
    )

    override fun execute(): Response<RetrogridResponse<R>> =
        Response.success(RetrogridResponse.UnknownError("An Unknown error occurred"))

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}



