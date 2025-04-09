package com.rega.core.data.repository

import com.rega.core.data.source.NetworkBoundResource
import com.rega.core.data.source.local.LocalDataSource
import com.rega.core.data.source.remote.RemoteDataSource
import com.rega.core.data.source.remote.network.ApiResponse
import com.rega.core.data.source.remote.response.ArticlesItem
import com.rega.core.domain.model.News
import com.rega.core.domain.repository.INewsRepository
import com.rega.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : INewsRepository {
    override fun getNews(): Flow<Results<List<News>>> =
        object : NetworkBoundResource<List<News>, List<ArticlesItem>>() {

            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map {
                    DataMapper.mapNewsEntityToNewsDomain(it)
                }
            }
            override fun shouldFetch(data: List<News>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>> =
                remoteDataSource.getNews()

            override suspend fun saveCallResult(data: List<ArticlesItem>) {
                val tourismList = DataMapper.mapNewsResponseToNewsEntity(data)
                localDataSource.insertNews(tourismList)
            }
        }.asFlow()

    override fun getFavoriteNews(): Flow<Results<List<News>>> {
        return localDataSource.getFavoriteNews().map {
            Results.Success(DataMapper.mapNewsEntityToNewsDomain(it))
        }
    }


    override suspend fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapNewsDomainToFavoriteNewsEntity(news)
        localDataSource.updateFavoriteNews(newsEntity, state)
    }
}