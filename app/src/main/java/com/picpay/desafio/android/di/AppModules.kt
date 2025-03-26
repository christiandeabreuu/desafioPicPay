package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.GetUsersUseCase
import com.picpay.desafio.android.MainViewModel
import com.picpay.desafio.android.PicPayService
import com.picpay.desafio.android.Retrofit
import com.picpay.desafio.android.UserRepository
import com.picpay.desafio.android.UserRepositoryImpl
import com.picpay.desafio.android.data.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    // Registra o repositório
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    // Registra o UseCase
    single { GetUsersUseCase(get()) }

    // Registra a ViewModel
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
