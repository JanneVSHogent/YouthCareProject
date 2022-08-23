package com.example.youthcareproject.favourites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youthcareproject.database.PostDao

class FavouritesViewModelFactory(
    private val dataSource: PostDao,
private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavouritesViewModel::class.java)){
            return FavouritesViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}