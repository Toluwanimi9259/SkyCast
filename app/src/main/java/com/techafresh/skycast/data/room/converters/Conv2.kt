package com.techafresh.skycast.data.room.converters

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.techafresh.skycast.data.dataClasses.forecast.Alerts
import com.techafresh.skycast.data.dataClasses.forecast.Current
import com.techafresh.skycast.data.dataClasses.forecast.ForecastX
import com.techafresh.skycast.data.dataClasses.forecast.Location


class Conv2 {

    private val gson = Gson()

    // Alerts

    @TypeConverter
    fun alertsToString(alerts: Alerts) : String{
        Log.d("Converters Alerts = " , gson.toJson(alerts))
       return gson.toJson(alerts)
    }
    @TypeConverter
    fun stringToAlert(alerts : String) : Alerts{
        return gson.fromJson(alerts , Alerts::class.java)
    }

    // Location

    @TypeConverter
    fun locationToString (location: Location) : String{
        Log.d("Converters Alerts = " , gson.toJson(location))
        return gson.toJson(location)
    }
    @TypeConverter
    fun stringToLocation (location: String) : Location{
        return gson.fromJson(location , Location::class.java)
    }

    // Current
    @TypeConverter
    fun currentToString(current : Current) : String{
        return gson.toJson(current)
    }

    @TypeConverter
    fun stringToCurrent(current : String) : Current{
        return gson.fromJson(current , Current::class.java)
    }

    // ForecastX
    @TypeConverter
    fun forecastXToString(forecastX: ForecastX) : String{
        return gson.toJson(forecastX)
    }

    @TypeConverter
    fun stringToForecastX(forecastX: String) : ForecastX{
        return gson.fromJson(forecastX , ForecastX::class.java)
    }

}