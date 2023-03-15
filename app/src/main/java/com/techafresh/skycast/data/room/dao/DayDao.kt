package com.techafresh.skycast.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.techafresh.skycast.data.dataClasses.forecast.Day
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveDayForecast(day: Day)

    @Query("SELECT * FROM forecast_day ORDER BY id ASC")
    fun getDayForecast() : Flow<List<Day>>
}