package com.app.work.demo.base
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment<V : ViewModel?> : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    abstract fun getViewModel(): Class<V>

    protected var viewModel: V? = null



    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
    }
}