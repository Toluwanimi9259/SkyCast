package com.techafresh.skycast.data.room.dao

import androidx.room.*
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: Forecast)

    @Delete
    suspend fun deleteForecast(forecast: Forecast)

    @Query("DELETE  from forecast")
    suspend fun deleteAll()

    @Query("SELECT * from forecast ORDER BY id DESC")
    suspend fun getForecast() : Forecast

}