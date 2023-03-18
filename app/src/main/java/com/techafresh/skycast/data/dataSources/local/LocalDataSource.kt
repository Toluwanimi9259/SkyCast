package com.techafresh.skycast.data.dataSources.local

import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveDayForecast(day: Day)

    fun getDayForecast() : Flow<List<Day>>

    suspend fun deleteAllDayForecast()

    // Full Forecast
    suspend fun saveForecast(forecast: Forecast)

    suspend fun deleteForecast(forecast: Forecast)

    suspend fun deleteAllForecast()

    suspend fun getForecast() : Forecast
}