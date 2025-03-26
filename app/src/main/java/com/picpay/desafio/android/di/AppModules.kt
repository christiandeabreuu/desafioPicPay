package com.picpay.desafio.android.di

import com.picpay.desafio.android.GetUsersUseCase
import com.picpay.desafio.android.MainViewModel
import com.picpay.desafio.android.Retrofit
import com.picpay.desafio.android.UserRepository
import com.picpay.desafio.android.UserRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    // Prover o UserRepository
    single<UserRepository> { UserRepositoryImpl(Retrofit.service) }

    // Prover o Use Case
    single { GetUsersUseCase(get()) }

    // Prover o ViewModel
    factory { MainViewModel(get()) }
}