package com.example.youthcareproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account (
    @PrimaryKey(autoGenerate = true)
    var accountId: Long = 0L,
    var email: String,
    val role: AccountRole = AccountRole.Teen,

    //Need converter
    //var posts: List<Post>,

    var firstName: String,
    var lastName: String,
    var description: String = "",
    var imageUrl: String = ""

    //Account has a role, username, password (done by Aiith0?)
    //Account has a list of posts
    //Account has a list of favourites
    //If account is begeleider, account has a list of team members
)