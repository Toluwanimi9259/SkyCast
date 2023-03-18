package com.techafresh.skycast.data.dataClasses.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    @ColumnInfo(name = "date" , defaultValue = "Problem")
    var date: String,

    val alerts: Alerts,
    val current: Current,
    val forecast: ForecastX,
    val location: Location
)