package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.local.UserDB
import com.picpay.desafio.android.data.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMappingTest {

    @Test
    fun `should convert User to UserDB`() {
        // Criação de um objeto User para teste
        val user = User(
            id = 1,
            name = "Chris",
            username = "Abreu",
            img = "url1"
        )

        val userDB = user.toUserDB()

        assertEquals(user.id, userDB.id)
        assertEquals(user.name, userDB.name)
        assertEquals(user.username, userDB.username)
        assertEquals(user.img, userDB.profilePicture)
    }

    @Test
    fun `should convert UserDB to User`() {
        val userDB = UserDB(
            id = 1,
            name = "Chris",
            username = "Abreu",
            profilePicture = "url1"
        )

        val user = userDB.toUser()

        assertEquals(userDB.id, user.id)
        assertEquals(userDB.name, user.name)
        assertEquals(userDB.username, user.username)
        assertEquals(userDB.profilePicture, user.img)
    }
}