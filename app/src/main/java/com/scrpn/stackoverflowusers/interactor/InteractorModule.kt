package com.scrpn.stackoverflowusers.interactor

import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideNetworkInteractor(): NetworkInteractor {
        return NetworkInteractor()
    }
}
