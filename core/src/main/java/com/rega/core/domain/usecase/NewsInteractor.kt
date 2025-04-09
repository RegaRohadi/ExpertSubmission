package com.rega.core.domain.usecase

import com.rega.core.domain.model.News
import com.rega.core.domain.repository.INewsRepository

class NewsInteractor (private val newsRepository: INewsRepository): NewsUseCase {
    override fun getNews() = newsRepository.getNews()

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override suspend fun setFavoriteNews(news: News, newState: Boolean) = newsRepository.setFavoriteNews(news, newState)
}