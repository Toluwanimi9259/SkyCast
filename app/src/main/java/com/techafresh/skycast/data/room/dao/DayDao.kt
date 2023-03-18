package com.techafresh.skycast.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techafresh.skycast.data.dataClasses.forecast.Day
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDayForecast(day: Day)

    @Query("SELECT * FROM forecast_day ORDER BY id ASC")
    fun getDayForecast() : Flow<List<Day>>

    @Query("DELETE  from forecast_day")
    suspend fun deleteAll()
}