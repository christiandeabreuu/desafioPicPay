package com.picpay.desafio.android

import com.picpay.desafio.android.data.UserDao



class UserRepositoryImpl(
    private val service: PicPayService,
    private val userDao: UserDao // Adicione o UserDao
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        val cachedUsers = userDao.getAllUsers().map { it.toUser() } // Recupera do banco
        if (cachedUsers.isNotEmpty()) {
            return cachedUsers // Retorna do cache se disponível
        }

        // Busca da API e salva no banco
        val apiUsers = service.getUsers()
        userDao.insertUsers(apiUsers.map { it.toUserDB() })
        return apiUsers
    }
}

