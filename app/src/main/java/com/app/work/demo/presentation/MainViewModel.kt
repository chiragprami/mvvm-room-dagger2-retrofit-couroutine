package com.app.work.demo.presentation

import GenReques
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.work.demo.data.remote.UserRepo
import com.app.work.demo.model.UserEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(userRepo: UserRepo) :
    ViewModel() {
    //lateinit var userResponse = GenReques<List<UserEntity>>()

    init {
          userRepo.listOfUser()
    }

    /*fun observeUserResponse(): MutableLiveData<List<UserEntity>> {
        return userResponse.liveData
    }*/

}

