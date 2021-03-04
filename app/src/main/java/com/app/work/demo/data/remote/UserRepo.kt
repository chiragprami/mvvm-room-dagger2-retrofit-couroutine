package com.app.work.demo.data.remote

import GenReques
import RestClient
import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.app.work.demo.data.local.dao.UserDao
import com.app.work.demo.model.UserEntity
import com.app.work.demo.model.UserResponse
import com.google.gson.JsonObject
import genObserver
import getResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepo   @Inject constructor(private val userDao: UserDao) {
    @SuppressLint("CheckResult")
    fun listOfUser(
   data: MutableLiveData<List<UserEntity>>
    ) {
        RestClient.getClient(RestClient.APIType.NOAUTH)
            .create(UserApi::class.java)
            .listOfUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                genObserver(JsonObject::class.java, success = { dataObj ->
                    var result = dataObj.getResult(UserResponse::class.java);
                    var listOfUsers = result.results;

                   data.value = listOfUsers
                }, error = { msg, code ->
                    var listOfResult: List<UserEntity>? = null
                    userDao?.let {
                        it.loadUserResult()?.let {
                            listOfResult = it.value!!
                        };
                    }
                    if (listOfResult != null) {
                     data.value = listOfResult
                    } else {
                     //   data.errorData.value = msg
                    }
                })
            )
    }

}