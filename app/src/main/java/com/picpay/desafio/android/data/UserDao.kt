package com.picpay.desafio.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserDB>)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserDB> // Deve retornar uma lista da entidade UserDB

}