package com.techafresh.skycast.domain.usecases

import com.techafresh.skycast.data.dataClasses.forecast.Astro
import com.techafresh.skycast.domain.repository.Repository
import retrofit2.Response

class GetAstroDetailsUseCase(private val repository: Repository) {
    suspend fun execute(date : String , location : String) : Response<Astro> = repository.getAstroDetails(date, location)
}