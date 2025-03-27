package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.ui.main.MainViewModel
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.local.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    single { GetUsersUseCase(get()) }

    viewModel { MainViewModel(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().userDao() }
}

val networkModule = module {
    single { retrofit2.Retrofit.Builder()
        .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/") // Substituir pela URL correta
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PicPayService::class.java) }
}
