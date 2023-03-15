package com.techafresh.skycast.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.room.converters.Conv2
import com.techafresh.skycast.data.room.converters.ConvertersX
import com.techafresh.skycast.data.room.dao.DayDao

@Database(entities = [Day::class] , exportSchema = false , version = 1)
@TypeConverters(ConvertersX::class)
abstract class ForecastDatabase :  RoomDatabase(){
    abstract fun dao() : DayDao
}