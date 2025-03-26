package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.appModule
import com.picpay.desafio.android.di.databaseModule
import com.picpay.desafio.android.di.networkModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule,  // Retrofit
                databaseModule, // AppDatabase e UserDao
                appModule       // MainViewModel, GetUsersUseCase e UserRepository
            )
        }
    }
}