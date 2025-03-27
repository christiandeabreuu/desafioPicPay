package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsersUseCase(private val userRepository: UserRepository) {
     operator fun invoke(): Flow<List<User>> {
        return flow {
            emit(userRepository.getUsers())
        }
    }
}