package com.scrpn.stackoverflowusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.StackOverflowUsersApplication

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(StackOverflowUsersApplication.injector)
    }

    protected abstract fun injectDependencies(injector: AppComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layoutResource, container, false)
    }

    fun navigateToFragment(fragment: Fragment) {
        activity?.let {
            if (it is MainActivity) {
                it.loadFragment(fragment)
            }
        }
    }

}
