package com.techafresh.skycast.data.dataSources

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.forecast.Astro
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getCurrentWeather(location : String) : Response<Current>

    suspend fun getWeatherForecast(location : String) : Response<Forecast>

    suspend fun getAstroDetails(date : String , location: String) : Response<Astro>
}