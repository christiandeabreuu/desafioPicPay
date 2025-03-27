package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.local.UserDB
import com.picpay.desafio.android.data.model.User

fun User.toUserDB(): UserDB {
    return UserDB(
        id = this.id,
        name = this.name,
        username = this.username,
        profilePicture = this.img
    )
}

fun UserDB.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.profilePicture
    )
}