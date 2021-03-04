package com.app.work.demo.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.work.demo.data.local.dao.UserDao
import com.app.work.demo.model.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}