package com.webronin_26.online_mart_retailer.data

import com.webronin_26.online_mart_retailer.data.source.RetailerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    private val repositoryModule : RetailerRepository = RetailerRepository()

    @Provides
    fun provideOnlineMartRepository() : RetailerRepository {
        return repositoryModule
    }
}