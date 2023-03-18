package com.techafresh.skycast.presentation.hilt

import com.techafresh.skycast.data.dataSources.local.LocalDataSource
import com.techafresh.skycast.data.dataSources.remote.RemoteDataSource
import com.techafresh.skycast.data.dataSourcesImpl.LocalDataSourceImpl
import com.techafresh.skycast.data.dataSourcesImpl.RemoteDataSourceImpl
import com.techafresh.skycast.data.retrofit.api.WeatherApi
import com.techafresh.skycast.data.room.dao.DayDao
import com.techafresh.skycast.data.room.dao.ForecastDao
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
    fun provideRemoteDataSource(weatherApi: WeatherApi) : RemoteDataSource {
        return RemoteDataSourceImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dayDao: DayDao , forecastDao: ForecastDao) : LocalDataSource{
        return LocalDataSourceImpl(dayDao, forecastDao)
    }
}