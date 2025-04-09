package com.rega.core.utils

import com.rega.core.data.source.local.entity.NewsEntity
import com.rega.core.data.source.remote.response.ArticlesItem
import com.rega.core.domain.model.News

object DataMapper {

    fun mapNewsResponseToNewsEntity(input: List<ArticlesItem>): List<NewsEntity> =
        input.map { item ->
            NewsEntity(
                NewsId = item.url ?: "",
                title = item.title,
                author = item.author,
                url = item.url,
                imageUrl = item.urlToImage ?: "",
                publishedAt = item.publishedAt ?: "",
                content = item.content ?: "",
                isFavorite = false
            )
        }

    fun mapNewsDomainToFavoriteNewsEntity(input: News) = NewsEntity(
        NewsId = input.NewsId ?: "",
        title = input.title,
        author = input.author,
        url = input.url,
        imageUrl = input.imageUrl,
        publishedAt = input.publishedAt,
        content = input.content,
        isFavorite = input.isFavorite
    )

    fun mapNewsEntityToNewsDomain(input: List<NewsEntity>): List<News> =
        input.map { entity ->
            News(
                NewsId = entity.NewsId,
                title = entity.title,
                author = entity.author,
                url = entity.url,
                imageUrl = entity.imageUrl,
                publishedAt = entity.publishedAt,
                content = entity.content,
                isFavorite = entity.isFavorite
            )
        }
}
