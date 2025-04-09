package com.rega.core.data.repository

sealed class Results<out T: Any?> {

    data object Loading : Results<Nothing>()

    data class Success<out T: Any>(val data: T) : Results<T>()

    data class Error(val errorMessage: String) : Results<Nothing>()
}