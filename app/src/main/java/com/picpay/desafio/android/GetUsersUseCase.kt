package com.picpay.desafio.android

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetUsersUseCase(private val userRepository: UserRepository) {
     operator fun invoke(): Flow<List<User>> {
        return flow {
            emit(userRepository.getUsers())
        }
    }
}