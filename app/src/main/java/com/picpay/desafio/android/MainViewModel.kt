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
            getUsersUseCase()
                .onStart {
                    // Define o estado como carregando antes de iniciar o fluxo
                    _state.postValue(UsersState(isLoading = true, isError = false))
                }
                .flowOn(Dispatchers.IO) // Certifica que o fluxo acontece em IO
                .catch { e ->
                    // Atualiza o estado com o erro, mantendo o carregamento como falso
                    _state.postValue(
                        UsersState(
                            errorMessage = e.message ?: "Erro desconhecido",
                            isLoading = false,
                            isError = true
                        )
                    )
                }
                .collect { users ->
                    // Atualiza o estado para parar o carregamento após obter os usuários
                    _state.postValue(UsersState(users = users, isLoading = false, isError = false))
                }
        }
    }
}