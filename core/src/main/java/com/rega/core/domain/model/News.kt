package com.rega.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val NewsId: String?,
    val title: String?,
    val author: String?,
    val url: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String,
    var isFavorite: Boolean
): Parcelable