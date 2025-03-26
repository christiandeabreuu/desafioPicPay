package com.picpay.desafio.android


class UserRepositoryImpl(private val service: PicPayService) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return service.getUsers()
    }
}