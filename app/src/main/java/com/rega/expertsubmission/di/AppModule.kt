package com.rega.expertsubmission.di

import com.rega.core.domain.usecase.NewsInteractor
import com.rega.core.domain.usecase.NewsUseCase
import com.rega.expertsubmission.presentation.DetailViewModel
import com.rega.expertsubmission.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}