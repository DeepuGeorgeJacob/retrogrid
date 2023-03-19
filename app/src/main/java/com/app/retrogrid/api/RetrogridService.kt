package com.app.retrogrid.api

import com.app.retrogrid.annotation.RetrofitServiceConfiguration
import com.app.retrogrid.model.NewsOverview
import com.app.retrogrid.response.RetrogridResponse
import retrofit2.http.GET

@RetrofitServiceConfiguration(baseUrl = "https://newsapi.org/v2/")
interface RetrogridService {
    @GET(value = "everything?domains=wsj.com&apiKey=a93a86ca5d144c2dbd676e53fb096c680")
    suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
}

