package com.techafresh.skycast.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.domain.repository.Repository
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import com.techafresh.skycast.domain.usecases.GetWeatherForecastUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    val currentWeatherLiveData : MutableLiveData<Response<Current>> = MutableLiveData()
    fun getCurrentWeatherData(location : String) = viewModelScope.launch {
        try {
            currentWeatherLiveData.postValue(getCurrentWeatherUseCase.execute(location))
        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL CURRENT" , "Error = " + ex.message)
        }
    }

    val weatherForecastLiveData : MutableLiveData<Response<Forecast>> = MutableLiveData()

    fun getWeatherForecast(location: String) = viewModelScope.launch {
        try {
            weatherForecastLiveData.postValue(getWeatherForecastUseCase.execute(location))
        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL FORECAST" , "Error = " + ex.message)
        }
    }
}