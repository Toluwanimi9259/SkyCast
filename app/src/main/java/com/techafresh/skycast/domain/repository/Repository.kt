package com.techafresh.skycast.domain.repository

import com.techafresh.skycast.data.dataClasses.current.Current
import retrofit2.Response

interface Repository {

    suspend fun getCurrentWeather(location : String) : Response<Current>
}