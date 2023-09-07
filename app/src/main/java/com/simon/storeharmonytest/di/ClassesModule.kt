package com.simon.storeharmonytest.di

import com.simon.storeharmonytest.data.sources.network.NetworkDataSource
import com.simon.storeharmonytest.data.sources.network.NetworkResponse
import com.simon.storeharmonytest.data.sources.network._NetworkDataSource
import com.simon.storeharmonytest.repositories.LocalRepository
import com.simon.storeharmonytest.repositories._LocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassesModules {

    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        localRepository: LocalRepository
    ): _LocalRepository

    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(
        networkDataSource: NetworkDataSource
    ): _NetworkDataSource
}