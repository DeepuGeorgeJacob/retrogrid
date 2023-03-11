package com.app.retrogrid.model


data class NewsErrorResponse(
    private val status: String,
    private val code: String,
    private val message: String
)