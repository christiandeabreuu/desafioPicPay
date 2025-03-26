package com.picpay.desafio.android

data class UsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isError: Boolean = false
)
