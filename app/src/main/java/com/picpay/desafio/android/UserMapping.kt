package com.picpay.desafio.android

import com.picpay.desafio.android.data.UserDB

fun User.toUserDB(): UserDB {
    return UserDB(
        id = this.id,
        name = this.name,
        username = this.username,
        profilePicture = this.img // Converte 'img' para 'profilePicture'
    )
}

fun UserDB.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.profilePicture // Converte 'profilePicture' para 'img'
    )
}