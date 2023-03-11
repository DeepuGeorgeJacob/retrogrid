package com.app.retrogrid.api

import com.app.retrogrid.error.ErrorResponseMap
import com.app.retrogrid.model.NewsErrorResponse
import com.app.retrogrid.model.NewsOverview
import com.app.retrogrid.response.RetrogridResponse
import retrofit2.http.GET


interface RetrogridService {
    @GET(value = "everything?domains=wsj.com&apiKey=a93a86ca5d144c2dbd676e53fb096c680")
    @ErrorResponseMap(errorClass = NewsErrorResponse::class)
    suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
}