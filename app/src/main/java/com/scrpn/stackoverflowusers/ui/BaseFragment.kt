package com.scrpn.stackoverflowusers.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun navigateToFragment(fragment: Fragment) {
        activity?.let {
            if (it is MainActivity) {
                it.loadFragment(fragment)
            }
        }
    }

    fun hideKeyboard() {
        activity?.let {
            val inputManager = it
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val currentFocusedView = it.currentFocus
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}
