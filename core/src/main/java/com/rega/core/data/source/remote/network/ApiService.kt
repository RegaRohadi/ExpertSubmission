package com.rega.core.data.source.remote.network

import com.rega.core.data.source.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String = "AI",
        @Query("apiKey") apiKey: String="90b732faf9e14541a902dd2c0b25df60"
    ): NewsResponse
}