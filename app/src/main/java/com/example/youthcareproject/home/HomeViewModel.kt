package com.example.youthcareproject.home

import android.app.Application
import androidx.lifecycle.*
import com.example.youthcareproject.database.Post
import com.example.youthcareproject.database.PostDao
import com.example.youthcareproject.formatPosts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class HomeViewModel(val database: PostDao, application: Application) : AndroidViewModel(application) {
    //Navigation LiveData
    private val _navigateToPostDetails = MutableLiveData<Long?>()
    val navigateToPostDetails: LiveData<Long?>
        get() = _navigateToPostDetails

    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //Get Posts
    val posts = database.getAllPosts()

    val postsString = Transformations.map(posts) { posts ->
        formatPosts(posts, application.resources)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onPostClicked(id: Long) {
        _navigateToPostDetails.value = id
    }

    fun onPostDetailsNavigated() {
        _navigateToPostDetails.value = null
    }

}