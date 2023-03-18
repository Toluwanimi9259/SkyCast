package com.techafresh.skycast.domain.repository

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.astronomy.Astro
import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getCurrentWeather(location : String) : Response<Current>

    suspend fun getWeatherForecast(location : String) : Response<Forecast>

    suspend fun getAstroDetails(date : String , location: String) : Response<Astro>

    // Old Still Gonna delete if full works

    suspend fun saveDayForecast(day: Day)

    fun getDayForecast() : Flow<List<Day>>

    suspend fun deleteAllDayForecast()


    // Full Forecast
    suspend fun saveForecast(forecast: Forecast)

    suspend fun deleteForecast(forecast: Forecast)

    suspend fun deleteAllForecast()

    suspend fun getForecast() : Forecast
}