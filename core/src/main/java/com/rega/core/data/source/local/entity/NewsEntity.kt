package com.rega.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "NewsId")
    val NewsId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)