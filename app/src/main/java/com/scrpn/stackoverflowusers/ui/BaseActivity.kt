package com.scrpn.stackoverflowusers.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.StackOverflowUsersApplication

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(StackOverflowUsersApplication.injector)
        setContentView(layoutResource)
    }

    protected abstract fun injectDependencies(injector: AppComponent)

    override fun onDestroy() {
        super.onDestroy()
    }
}
