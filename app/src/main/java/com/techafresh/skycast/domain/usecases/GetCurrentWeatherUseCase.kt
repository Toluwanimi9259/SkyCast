package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.domain.repository.Repository
import retrofit2.Response

class GetCurrentWeatherUseCase(private val repository: Repository){
  suspend fun execute(location : String) : Response<Current> = repository.getCurrentWeather(location)
}