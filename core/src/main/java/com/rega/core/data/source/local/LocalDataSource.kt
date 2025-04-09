package com.rega.core.data.source.local

import com.rega.core.data.source.local.entity.NewsEntity
import com.rega.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val newsDao: NewsDao) {

    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()
    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    suspend fun insertNews(news: List<NewsEntity>) = newsDao.insertNews(news)
    suspend fun updateFavoriteNews(news: NewsEntity, newState: Boolean) {
        news.isFavorite = newState
        newsDao.updateFavoriteNews(news)
    }
}