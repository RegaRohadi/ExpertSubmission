package com.rega.expertsubmission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rega.core.data.repository.Results
import com.rega.core.domain.model.News
import com.rega.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _results = MutableLiveData<Results<News>>()
    val results: LiveData<Results<News>> = _results

    fun setNews(news: News) {
        _results.value = Results.Success(news)
    }

    fun setFavoriteAnime(news: News, newState: Boolean) {
        viewModelScope.launch {
            newsUseCase.setFavoriteNews(news, newState)
            _results.value = Results.Success(news.copy(isFavorite = newState))
        }
    }
}


