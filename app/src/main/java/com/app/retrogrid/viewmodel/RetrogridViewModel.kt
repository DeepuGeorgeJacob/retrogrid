package com.app.retrogrid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.retrogrid.api.NewsRepository
import com.app.retrogrid.model.Article
import com.app.retrogrid.model.NewsErrorResponse
import com.app.retrogrid.model.NewsOverview
import com.app.retrogrid.response.RetrogridResponse
import kotlinx.coroutines.launch


class RetrogridViewModel(private val repository: NewsRepository = NewsRepository()) : ViewModel() {
    private val _newsOverview = MutableLiveData<List<Article>>()
    val newsOverview:LiveData<List<Article>> = _newsOverview
    init {
        viewModelScope.launch {
            when (val result = repository.getNewsOverView()) {
                is RetrogridResponse.Success -> _newsOverview.value = result.responseBody.articles
                is RetrogridResponse.Failure -> {
                    println((result.errorResponseBody as NewsErrorResponse).toString())
                }
                is RetrogridResponse.NetworkError -> println("Network error : "+result.errorMessage)
                is RetrogridResponse.UnknownError -> println("Unknown error : "+result.errorMessage)
            }
        }

    }
}