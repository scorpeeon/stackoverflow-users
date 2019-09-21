package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.domain.InMemoryDataSource
import com.scrpn.stackoverflowusers.network.NetworkDataSource
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideApiInteractor(networkDataSource: NetworkDataSource,
                             inMemoryDataSource: InMemoryDataSource): ApiInteractor {
        return ApiInteractor(networkDataSource, inMemoryDataSource)
    }
}
