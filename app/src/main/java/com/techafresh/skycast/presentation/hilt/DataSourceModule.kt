package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.data.dataSources.RemoteDataSource
import com.techafresh.skycast.data.dataSourcesImpl.RemoteDataSourceImpl
import com.techafresh.skycast.data.retrofit.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(weatherApi: WeatherApi) : RemoteDataSource{
        return RemoteDataSourceImpl(weatherApi)
    }
}