package com.rega.expertsubmission.favorite.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}