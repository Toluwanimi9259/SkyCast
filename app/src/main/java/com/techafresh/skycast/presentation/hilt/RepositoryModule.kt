package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.data.dataSources.RemoteDataSource
import com.techafresh.skycast.data.dataSourcesImpl.RepositoryImpl
import com.techafresh.skycast.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource) : Repository{
        return RepositoryImpl(remoteDataSource)
    }

}