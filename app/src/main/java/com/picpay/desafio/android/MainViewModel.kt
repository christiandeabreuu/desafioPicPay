package com.picpay.desafio.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _state = MutableLiveData<UsersState>()
    val state: LiveData<UsersState> = _state

    fun fetchUsers() {
        viewModelScope.launch {
            getUsersUseCase().flowOn(Dispatchers.IO).onStart {
                    _state.postValue(UsersState(isLoading = true, isError = false))
                }.catch { e ->
                    _state.postValue(
                        UsersState(
                            errorMessage = e.message ?: "Erro desconhecido",
                            isLoading = false,
                            isError = true
                        )
                    )
                }.collect { users ->
                    _state.postValue(UsersState(users = users, isLoading = false, isError = false))
                }
        }
    }
}