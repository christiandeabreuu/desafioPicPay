package com.picpay.desafio.android


class UserRepository(private val service: PicPayService) {

    suspend fun getUsers(): List<User> {
        return service.getUsers()
    }
}