package com.scrpn.stackoverflowusers

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
object ApplicationModule {
    @Singleton
    @Provides
    @JvmStatic
    internal fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}
