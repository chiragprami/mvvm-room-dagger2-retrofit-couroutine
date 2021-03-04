package com.app.work.demo

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.app.work.demo.data.local.UserDataBase
import com.app.work.demo.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
        setInstance(this)
    }

    private fun initializeComponent() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector
    }

    companion object {
        var appContext: MyApplication? = null
            private set
        @Synchronized
        private fun setInstance(app: MyApplication) {
            appContext = app

        }
    }
}