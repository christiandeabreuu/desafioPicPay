package com.picpay.desafio.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.GetUsersUseCase
import com.picpay.desafio.android.MainViewModel


//class MainViewModelFactory(private val getUsersUseCase: GetUsersUseCase) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(getUsersUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}