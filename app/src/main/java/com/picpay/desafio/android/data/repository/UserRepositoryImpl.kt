package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.utils.toUser
import com.picpay.desafio.android.utils.toUserDB


class UserRepositoryImpl(
    private val service: PicPayService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        val cachedUsers = userDao.getAllUsers().map { it.toUser() }
        if (cachedUsers.isNotEmpty()) {
            return cachedUsers
        }

        val apiUsers = service.getUsers()
        userDao.insertUsers(apiUsers.map { it.toUserDB() })
        return apiUsers
    }
}

