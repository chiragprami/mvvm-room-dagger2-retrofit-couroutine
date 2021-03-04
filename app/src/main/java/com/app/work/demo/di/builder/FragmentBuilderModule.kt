package com.app.work.demo.di.builder

import com.app.work.demo.view.MainFragment
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
 abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

}