package com.example.youthcareproject.profile


import androidx.lifecycle.ViewModel
import com.example.youthcareproject.database.AccountDao

class ProfileViewModel(accountEmail: String = "", database: AccountDao) : ViewModel(){

    //Get Account
    val account = database.getAccountByEmail(accountEmail)

}