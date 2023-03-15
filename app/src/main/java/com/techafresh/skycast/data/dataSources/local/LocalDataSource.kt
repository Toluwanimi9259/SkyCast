package com.techafresh.skycast.data.dataSources.local

import com.techafresh.skycast.data.dataClasses.forecast.Day
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveDayForecast(day: Day)

    fun getDayForecast() : Flow<List<Day>>
}