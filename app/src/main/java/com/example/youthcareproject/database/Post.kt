package com.example.youthcareproject.database

import android.accounts.Account
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    var postId: Long = 0L,
    var description: String = "",
    var url: String = "",
    // TO DO, should be final but gives error
    var createdAt: Long = System.currentTimeMillis(),
    var favourited: Boolean = false

    //TO DO, cant be null, should be logged in user
    //var creator: Account? = null,

    //should determine what type of post
    //add url, image or just text based on that
    //Post has one account
)
