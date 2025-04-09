package com.rega.core.domain.usecase

import com.rega.core.data.repository.Results
import com.rega.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getNews(): Flow<Results<List<News>>>
    fun getFavoriteNews(): Flow<Results<List<News>>>
    suspend fun setFavoriteNews(news: News, newState: Boolean)
}