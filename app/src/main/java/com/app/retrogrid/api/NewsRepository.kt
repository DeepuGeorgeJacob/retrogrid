package com.app.retrogrid.api

import com.app.retrogrid.network.RetroGridNetworkClient


class NewsRepository(
    private val service: RetrogridService = RetroGridNetworkClient.buildService(RetrogridService::class.java)
) {
    suspend fun getNewsOverView() = service.fetchNewsOverview()
}