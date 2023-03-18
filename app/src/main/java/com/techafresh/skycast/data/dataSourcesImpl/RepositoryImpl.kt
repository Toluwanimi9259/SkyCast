package com.techafresh.skycast.data.dataSourcesImpl

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.astronomy.Astro
import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.data.dataSources.local.LocalDataSource
import com.techafresh.skycast.data.dataSources.remote.RemoteDataSource
import com.techafresh.skycast.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource , private val localDataSource: LocalDataSource) : Repository{

    override suspend fun getCurrentWeather(location : String): Response<Current> = remoteDataSource.getCurrentWeather(location)
    override suspend fun getWeatherForecast(location: String): Response<Forecast> = remoteDataSource.getWeatherForecast(location)
    override suspend fun getAstroDetails(date: String, location: String): Response<Astro> = remoteDataSource.getAstroDetails(date, location)

    override suspend fun saveDayForecast(day: Day) {
        localDataSource.saveDayForecast(day)
    }

    override fun getDayForecast(): Flow<List<Day>> = localDataSource.getDayForecast()
    override suspend fun deleteAllDayForecast() {
        localDataSource.deleteAllDayForecast()
    }

    override suspend fun saveForecast(forecast: Forecast) {
        localDataSource.saveForecast(forecast)
    }

    override suspend fun deleteForecast(forecast: Forecast) {
        localDataSource.deleteForecast(forecast)
    }

    override suspend fun deleteAllForecast() {
        localDataSource.deleteAllForecast()
    }

    override suspend fun getForecast(): Forecast {
        return localDataSource.getForecast()
    }

}