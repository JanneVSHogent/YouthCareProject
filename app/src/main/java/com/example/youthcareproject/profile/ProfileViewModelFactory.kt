package com.example.youthcareproject.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youthcareproject.database.AccountDao
import com.example.youthcareproject.database.PostDao
import com.example.youthcareproject.detailsPost.DetailsPostViewModel
import java.lang.IllegalArgumentException

class ProfileViewModelFactory(private val accountEmail: String, private val dataSource: AccountDao)
    : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(accountEmail, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}