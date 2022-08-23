package com.example.youthcareproject.favourites

import android.app.Application
import androidx.lifecycle.*
import com.example.youthcareproject.database.PostDao
import com.example.youthcareproject.formatPosts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouritesViewModel(val database: PostDao, application: Application) : AndroidViewModel(application) {

    val posts = database.getAllPosts()


    val postsString = Transformations.map(posts) { posts ->
        formatPosts(posts, application.resources)
    }

}