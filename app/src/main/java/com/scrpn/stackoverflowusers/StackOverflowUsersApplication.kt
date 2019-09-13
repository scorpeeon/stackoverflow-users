package com.scrpn.stackoverflowusers

import android.app.Application

class StackOverflowUsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupInjector()
        injectDependencies(injector)
    }

    protected fun setupInjector() {
        injector = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    protected fun injectDependencies(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        lateinit var injector: AppComponent
    }
}
