package com.techafresh.skycast.data.dataSourcesImpl

import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.data.dataSources.local.LocalDataSource
import com.techafresh.skycast.data.room.dao.DayDao
import com.techafresh.skycast.data.room.dao.ForecastDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val dayDao: DayDao , private val forecastDao: ForecastDao) : LocalDataSource {

    override suspend fun saveDayForecast(day: Day) {
        dayDao.saveDayForecast(day)
    }

    override fun getDayForecast(): Flow<List<Day>> = dayDao.getDayForecast()
    override suspend fun deleteAllDayForecast() {
        dayDao.deleteAll()
    }

    override suspend fun saveForecast(forecast: Forecast) {
        forecastDao.insertForecast(forecast)
    }

    override suspend fun deleteForecast(forecast: Forecast) {
        forecastDao.deleteForecast(forecast)
    }

    override suspend fun deleteAllForecast() {
        forecastDao.deleteAll()
    }

    override suspend fun getForecast(): Forecast {
        return forecastDao.getForecast()
    }
}