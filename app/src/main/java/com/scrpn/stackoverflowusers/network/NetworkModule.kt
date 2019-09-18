package com.scrpn.stackoverflowusers.network


import com.scrpn.stackoverflowusers.BuildConfig

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import java.util.*


@Module
object NetworkModule {
    @Singleton
    @Provides
    @JvmStatic
    internal fun provideApplicationContext(): Retrofit {

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.STACK_EXCHANGE_API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    internal fun provideStackExchangeApi(retrofit: Retrofit): StackExchangeApi {
        return retrofit.create(StackExchangeApi::class.java)
    }
}
