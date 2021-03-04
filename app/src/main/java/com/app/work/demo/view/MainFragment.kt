package com.app.work.demo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.app.work.demo.R
import com.app.work.demo.base.BaseFragment
import com.app.work.demo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import loge

class MainFragment : BaseFragment<MainViewModel>() {

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleResult()
    }

    private fun handleResult() {
        viewModel?.observeUserResponse()?.observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                rvDat.adapter = UsersAdapter(mContext = this, list = data)
                viewModel?.updateDatabase()
            }
            loge("data_list ${it.size.toString()}")
        })
        viewModel?.getData()
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }



}