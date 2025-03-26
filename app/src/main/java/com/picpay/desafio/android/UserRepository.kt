package com.picpay.desafio.android


interface UserRepository {
    suspend fun getUsers(): List<User>
}