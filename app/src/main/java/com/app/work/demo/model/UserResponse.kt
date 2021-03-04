package com.app.work.demo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserResponse(
    val results: List<UserEntity>,
    val info: Info
)

data class Info(
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)

@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    //  val name: Any,
    //  val location: Any,

    //  val login: Any,
//    val registered: Any,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("cell")
    val cell: String,
    //  val id: Any,
    // val picture: Any,
    @SerializedName("nat")
    val nat: String
)

data class Dob(
    val date: String,
    val age: Long
)

data class ID(
    val name: String,
    val value: Any? = null
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Long,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Street(
    val number: Long,
    val name: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)