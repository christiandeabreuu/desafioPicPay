package com.picpay.desafio.android


import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = getUsersUseCase() 
                _userList.postValue(users)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Erro desconhecido")
            }
        }
    }
}