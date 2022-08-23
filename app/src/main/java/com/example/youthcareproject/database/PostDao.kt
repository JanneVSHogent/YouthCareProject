package com.example.youthcareproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {

    //Posts
    @Insert
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Query("SELECT * from posts WHERE postId = :key")
    fun getPost(key: Long): Post

    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    fun getAllPosts(): LiveData<List<Post>>

    @Query("DELETE FROM posts")
    fun clearPosts()
}