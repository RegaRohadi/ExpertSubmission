package com.rega.expertsubmission.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rega.core.data.repository.Results
import com.rega.core.domain.model.News
import com.rega.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel (private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _results = MutableLiveData<Results<List<News>>>(Results.Loading)
    val results: LiveData<Results<List<News>>> = _results

    fun getFavoriteNews() {
        viewModelScope.launch {
            try {
                newsUseCase.getFavoriteNews().collect { result ->
                    _results.postValue(result)
                }
            } catch (e: Exception) {
                _results.postValue(Results.Error(e.message.toString()))
            }
        }
    }
}