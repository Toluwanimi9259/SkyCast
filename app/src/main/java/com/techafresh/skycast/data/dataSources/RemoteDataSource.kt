package com.techafresh.skycast.data.dataSources

import com.techafresh.skycast.data.dataClasses.current.Current
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getCurrentWeather(location : String) : Response<Current>
}