package com.example.youthcareproject.detailsPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youthcareproject.database.Post
import com.example.youthcareproject.database.PostDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DetailsPostViewModel(private val postId: Long = 0L,val database: PostDao)
        : ViewModel() {

    //Post LiveData
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    //Api status LiveData
    //private val _status = MutableLiveData<ApiStatus>()
    //val status: LiveData<ApiStatus>
        //get() = _status

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}