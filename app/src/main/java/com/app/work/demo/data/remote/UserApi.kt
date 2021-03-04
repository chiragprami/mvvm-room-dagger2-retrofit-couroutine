package com.app.work.demo.data.remote
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("/api/?results=100")
    fun listOfUsers(): Observable<Response<JsonObject>>

}
