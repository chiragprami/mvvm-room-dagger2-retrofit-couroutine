package com.app.work.demo.data.remote

import RestClient
import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.app.work.demo.data.local.dao.UserDao
import com.app.work.demo.model.UserEntity
import com.app.work.demo.model.UserResponse
import com.google.gson.JsonObject
import getResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import loge
import logv
import retrofit2.Response
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {
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
                getUserObserver(userDao, JsonObject::class.java, success = { dataObj ->
                    var result = dataObj.getResult(UserResponse::class.java);
                    var listOfUsers = result.results;

                    data.value = listOfUsers
                }, error = { msg, code ->

                }, offlineData = {
                    data.value = it
                })
            )
    }

}


fun <T> getUserObserver(
    userDao: UserDao,
    serviceClass: Class<T>,
    success: (T) -> Unit,
    error: (String, Int) -> Unit,
    offlineData: (result: List<UserEntity>) -> Unit
) =
    object : DisposableObserver<Response<T>>() {
        override fun onError(e: Throwable) {
            e.printStackTrace()
            loge("onError  @999 " + e.message)
            var listOfResult: List<UserEntity> = ArrayList()
            userDao?.let {
                it.loadUserResult()?.let {data->
                    data.value?.let{
                        listOfResult = it
                    }
                };
            }
            listOfResult?.let { offlineData(it) };

            if (listOfResult == null) {
                error(e.message!!, 999)
            }
        }

        override fun onNext(response: Response<T>) {
            when (val statusCode = response.code()) {
                200 -> {
                    success(response.body()!!)
                }
                else -> {
                    error(response.errorBody()!!.string(), statusCode)
                }
            }
        }

        override fun onComplete() {
            logv("onComplete ${serviceClass.name}")
        }
    }

