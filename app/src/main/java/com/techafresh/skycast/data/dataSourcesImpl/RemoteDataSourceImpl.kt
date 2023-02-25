package com.techafresh.skycast.data.dataSourcesImpl

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataSources.RemoteDataSource
import com.techafresh.skycast.data.retrofit.api.WeatherApi
import retrofit2.Response

class RemoteDataSourceImpl(private val weatherApi: WeatherApi) : RemoteDataSource {

    override suspend fun getCurrentWeather(location: String): Response<Current> = weatherApi.getCurrentWeatherForecast(location)
}