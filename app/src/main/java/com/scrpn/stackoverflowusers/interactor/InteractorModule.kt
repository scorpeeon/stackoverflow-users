package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.network.StackExchangeApi
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNetworkInteractor(apiService: StackExchangeApi): NetworkInteractor {
        return NetworkInteractor(apiService)
    }
}
