package com.app.work.demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.work.demo.model.UserEntity


@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    fun loadUserResult(): LiveData<List<UserEntity>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveArticles(userEntity: List<UserEntity?>?)
}
