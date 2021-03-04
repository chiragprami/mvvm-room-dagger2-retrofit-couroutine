package com.app.work.demo.viewmodel

import GenReques
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.work.demo.MyApplication
import com.app.work.demo.data.remote.UserRepo
import com.app.work.demo.model.UserEntity
import javax.inject.Inject


class MainViewModel @Inject constructor() :
    ViewModel() {

    private val userResponse: MutableLiveData<List<UserEntity>> = MutableLiveData<List<UserEntity>>()


    fun  getData(){
        MyApplication.db?.userDao()?.let { UserRepo(it).listOfUser(userResponse) }
    }

    fun observeUserResponse(): MutableLiveData<List<UserEntity>> {
        return userResponse
    }

}

