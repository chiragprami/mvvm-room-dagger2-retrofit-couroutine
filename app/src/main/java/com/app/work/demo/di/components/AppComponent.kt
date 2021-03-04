package com.app.work.demo.di.components

import android.app.Application
import com.app.work.demo.MyApplication
import com.app.work.demo.di.builder.ActivityBuilderModule
import com.app.work.demo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilderModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}