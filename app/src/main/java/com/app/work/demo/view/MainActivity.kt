package com.app.work.demo.view

import android.os.Bundle
import com.app.work.demo.R
import com.app.work.demo.base.BaseActivity


class MainActivity : BaseActivity() {

    override val layoutRes: Int
        get() =  R.layout.main_activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}