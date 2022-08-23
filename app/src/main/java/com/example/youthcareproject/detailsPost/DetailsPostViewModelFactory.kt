package com.example.youthcareproject.detailsPost

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youthcareproject.database.PostDao
import java.lang.IllegalArgumentException

class DetailsPostViewModelFactory(private val postId: Long, private val dataSource: PostDao)
        : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsPostViewModel::class.java)) {
            return DetailsPostViewModel(postId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}