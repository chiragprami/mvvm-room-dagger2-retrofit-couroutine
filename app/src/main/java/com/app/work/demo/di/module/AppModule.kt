package com.app.work.demo.di.module

import android.app.Application
import androidx.room.Room
import com.app.work.demo.data.local.UserDataBase
import com.app.work.demo.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(application: Application): UserDataBase {
        return Room.databaseBuilder(application, UserDataBase::class.java, "users_result.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userDataBase: UserDataBase): UserDao {
        return userDataBase.userDao()
    }
}