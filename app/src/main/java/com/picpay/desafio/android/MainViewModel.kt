package com.picpay.desafio.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = userRepository.getUsers()
                _userList.postValue(users)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Erro desconhecido")
            }
        }
    }
}