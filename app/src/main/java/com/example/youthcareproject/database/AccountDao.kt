package com.example.youthcareproject.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("SELECT * from accounts WHERE accountId = :key")
    fun getAccount(key: Long): Account

    @Query("SELECT * from accounts WHERE email = :key")
    fun getAccountByEmail(key: String): Account
}