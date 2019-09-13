package com.scrpn.stackoverflowusers.network


import com.scrpn.stackoverflowusers.BuildConfig

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    @Singleton
    @Provides
    @JvmStatic
    internal fun provideApplicationContext(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.STACK_EXCHANGE_API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    internal fun provideStackExchangeApi(retrofit: Retrofit): StackExchangeApi {
        return retrofit.create(StackExchangeApi::class.java)
    }
}
