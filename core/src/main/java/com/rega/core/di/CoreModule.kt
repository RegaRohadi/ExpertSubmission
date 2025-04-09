package com.rega.core.di

import androidx.room.Room
import com.rega.core.data.repository.NewsRepository
import com.rega.core.data.source.local.LocalDataSource
import com.rega.core.data.source.local.room.NewsDatabase
import com.rega.core.data.source.remote.RemoteDataSource
import com.rega.core.data.source.remote.network.ApiService
import com.rega.core.domain.repository.INewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, "Tourism.db"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<NewsDatabase>().newsDao() }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<INewsRepository> { NewsRepository(get(), get()) }
}