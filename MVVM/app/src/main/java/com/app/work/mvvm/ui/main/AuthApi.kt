package com.app.work.mvvm.ui.main
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("api/endPoint")
    fun listOfString(/*@Body body: RequestBody*/): Observable<Response<JsonObject>>

}
