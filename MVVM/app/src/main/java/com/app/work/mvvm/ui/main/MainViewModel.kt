package com.app.work.mvvm.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.Serializable


class MainViewModel : ViewModel() {
    private var listOfString = MutableLiveData<List<String>>()

    fun observeListOfString(): MutableLiveData<List<String>> {
        return listOfString
    }


}

@SuppressLint("CheckResult")
fun pushData(
):List<String> {

    RestClient.getClient(RestClient.APIType.NOAUTH)
        .create(AuthApi::class.java)
        .listOfString()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(
            genObserver(JsonObject::class.java, success = { dataObj ->
            }, error = { msg, code ->
            })
        )
}
fun <T> genObserver(serviceClass: Class<T>, success: (T) -> Unit, error: (String, Int) -> Unit) =
    object : DisposableObserver<Response<T>>() {
        override fun onError(e: Throwable) {
            e.printStackTrace()
            error(e.message!!, 999)
        }

        override fun onNext(response: Response<T>) {
         var  statusCode = response.code()
            when (statusCode) {
                200 -> {
                    success(response.body()!!)
                }
                else -> {
                    error("", statusCode)
                }
            }
        }

        override fun onComplete() {
        }
    }


