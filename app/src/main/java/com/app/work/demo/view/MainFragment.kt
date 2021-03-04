package com.app.work.demo.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.app.work.demo.R
import com.app.work.demo.base.BaseFragment
import com.app.work.demo.presentation.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import loge

class MainFragment : BaseFragment<MainViewModel>() {

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleResult()
    }

    private fun handleResult() {
       /* viewModel.observeUserResponse().observe(viewLifecycleOwner, Observer {

            it?.let { data ->
                val adapter = UsersAdapter(mContext = this, list = data)
                rvDat.adapter = adapter
            }

            loge("data_list ${it.size.toString()}")
        })*/
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override val layoutRes: Int
        get() = R.layout.main_fragment

}