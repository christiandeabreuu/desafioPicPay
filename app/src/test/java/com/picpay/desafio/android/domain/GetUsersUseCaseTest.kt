package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUsersUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private val getUsersUseCase = GetUsersUseCase(userRepository)

        @Test
        fun `should emit user list from repository`() = runBlocking {
            val mockUsers = listOf(
                User(id = 1, name = "Chris Abreu", username = "123", img = "url1"),
                User(id = 2, name = "Manuela Marcal", username = "456", img = "url2")
            )

            // retorna a lista simulada
            coEvery { userRepository.getUsers() } returns mockUsers

            // Testa o fluxo
            val result = getUsersUseCase().first()

            // Valida o resultado
            assertEquals(mockUsers, result)
        }


    @Test(expected = Exception::class)
    fun `should throw exception when repository fails`(): Unit = runBlocking {
        // Configuração do mock para lançar uma exceção
        coEvery { userRepository.getUsers() } throws Exception("Repository error")

        // Executa o use case
        getUsersUseCase().first() // Deve lançar a exceção
    }
}
