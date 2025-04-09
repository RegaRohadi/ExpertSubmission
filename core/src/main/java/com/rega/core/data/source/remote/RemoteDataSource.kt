package com.rega.core.data.source.remote

import com.rega.core.data.source.remote.network.ApiResponse
import com.rega.core.data.source.remote.network.ApiService
import com.rega.core.data.source.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getNews( ): Flow<ApiResponse<List<ArticlesItem>>> = flow {
        try {
            val response = apiService.getNews()

            val articles = response.articles ?: emptyList()

            if (articles.isNotEmpty()) {
                emit(ApiResponse.Success(articles))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
