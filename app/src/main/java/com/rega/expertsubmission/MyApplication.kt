package com.rega.expertsubmission

import android.app.Application
import com.rega.core.di.databaseModule
import com.rega.core.di.networkModule
import com.rega.core.di.repositoryModule
import com.rega.expertsubmission.di.useCaseModule
import com.rega.expertsubmission.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
            androidLogger(Level.DEBUG)
        }
    }
}