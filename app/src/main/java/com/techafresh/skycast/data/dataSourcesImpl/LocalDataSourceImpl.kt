package com.techafresh.skycast.data.dataSourcesImpl

import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataSources.local.LocalDataSource
import com.techafresh.skycast.data.room.dao.DayDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val dayDao: DayDao) : LocalDataSource {

    override suspend fun saveDayForecast(day: Day) {
        dayDao.saveDayForecast(day)
    }

    override fun getDayForecast(): Flow<List<Day>> = dayDao.getDayForecast()
}