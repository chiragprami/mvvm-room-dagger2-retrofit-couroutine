package com.app.work.demo.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.work.demo.data.local.dao.UserDao
import com.app.work.demo.data.remote.UserRepo
import com.app.work.demo.model.UserEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel @Inject constructor(val userRepo: UserRepo, val userDao: UserDao) :
    ViewModel() {
    private val userResponse: MutableLiveData<List<UserEntity>> =
        MutableLiveData<List<UserEntity>>()

    fun getData() {
        userRepo.let { it.listOfUser(userResponse) }
    }

    fun observeUserResponse(): MutableLiveData<List<UserEntity>> {
        return userResponse
    }

    @SuppressLint("CheckResult")
    fun updateDatabase() {
        Observable.just(userDao)
            .subscribeOn(Schedulers.io())
            .subscribe { db -> // database operation }
                db.saveArticles(userEntity = userResponse.value)
            }
    }
}

