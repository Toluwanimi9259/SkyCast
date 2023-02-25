package com.techafresh.skycast.data.retrofit.api

import com.techafresh.skycast.BuildConfig
import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/current.json?")
    suspend fun getCurrentWeatherForecast(
        @Query("q")
        location : String,

        @Query("key")
        apiKey : String = BuildConfig.API_KEY,

        @Query("aqi")
        airQuality : String = "yes"
    ) : Response<Current>

    @GET("/v1/forecast.json?")
    suspend fun getWeatherForecast(

        @Query("q")
        location: String,

        @Query("key")
        apiKey: String = BuildConfig.API_KEY,

        @Query("aqi")
        airQuality : String = "yes",

        @Query("alerts")
        alerts : String = "yes",

        @Query("days")
        days : Int = 7
    ) : Response<Forecast>
}