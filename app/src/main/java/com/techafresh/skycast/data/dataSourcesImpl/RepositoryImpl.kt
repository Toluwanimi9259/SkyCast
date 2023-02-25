package com.techafresh.skycast.data.dataSourcesImpl

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.forecast.Astro
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.data.dataSources.RemoteDataSource
import com.techafresh.skycast.domain.repository.Repository
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository{

    override suspend fun getCurrentWeather(location : String): Response<Current> = remoteDataSource.getCurrentWeather(location)
    override suspend fun getWeatherForecast(location: String): Response<Forecast> = remoteDataSource.getWeatherForecast(location)
    override suspend fun getAstroDetails(date: String, location: String): Response<Astro> = remoteDataSource.getAstroDetails(date, location)

}