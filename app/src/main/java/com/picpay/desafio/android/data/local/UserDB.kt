package com.picpay.desafio.android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDB(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val profilePicture: String
)