package com.app.retrogrid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.retrogrid.api.NewsRepository
import com.app.retrogrid.model.NewsErrorResponse
import com.app.retrogrid.response.RetrogridResponse
import kotlinx.coroutines.launch


class RetrogridViewModel(private val repository: NewsRepository = NewsRepository()) : ViewModel() {

    init {
        viewModelScope.launch {
            when (val result = repository.getNewsOverView()) {
                is RetrogridResponse.Success -> println("SUCCESS ${result.responseBody}")
                is RetrogridResponse.Failure -> {
                    println((result.errorResponseBody as NewsErrorResponse).toString())
                }
                is RetrogridResponse.NetworkError -> println("Network error : "+result.errorMessage)
                is RetrogridResponse.UnknownError -> println("Unknown error : "+result.errorMessage)
            }
        }

    }

}