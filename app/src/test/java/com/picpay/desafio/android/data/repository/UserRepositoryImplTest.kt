package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDB
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.utils.toUser
import com.picpay.desafio.android.utils.toUserDB
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryImplTest {

    private val mockService: PicPayService = mockk()
    private val mockUserDao: UserDao = mockk()
    private val userRepository = UserRepositoryImpl(mockService, mockUserDao)

    @Test
    fun `should return cached users if cache is not empty`() = runBlocking {
        // Mockando usuários em cache
        val cachedUsers = listOf(
            UserDB(1, "Chris", "Abreu", "url1"),
            UserDB(2, "Manu", "Marcal", "url2")
        )
        coEvery { mockUserDao.getAllUsers() } returns cachedUsers

        // Executa o método do repositório
        val result = userRepository.getUsers()

        // Valida se os dados do cache foram retornados
        assertEquals(cachedUsers.map { it.toUser() }, result)
        coVerify(exactly = 0) { mockService.getUsers() }
    }

    @Test
    fun `should fetch users from API and cache them if cache is empty`() = runBlocking {
        // Mockando cache vazio
        coEvery { mockUserDao.getAllUsers() } returns emptyList()

        // Mockando retorno da API
        val apiUsers = listOf(
            User("a", "Chris", 1, "url1"),
            User("b", "Marcal", 2, "url2")
        )
        coEvery { mockService.getUsers() } returns apiUsers
        coEvery { mockUserDao.insertUsers(any()) } returns Unit

        val result = userRepository.getUsers()

        assertEquals(apiUsers, result)
        coVerify(exactly = 1) { mockService.getUsers() } // Certifica que a API foi chamada
        coVerify(exactly = 1) { mockUserDao.insertUsers(apiUsers.map { it.toUserDB() }) } // Verifica se foi salvo no cache
    }
}