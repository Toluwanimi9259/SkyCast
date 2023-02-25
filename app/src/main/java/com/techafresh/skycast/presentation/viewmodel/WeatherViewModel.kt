package com.techafresh.skycast.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techafresh.skycast.data.dataClasses.current.Current
import com.techafresh.skycast.domain.usecases.GetCurrentWeatherUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    val currentWeatherLiveData : MutableLiveData<Response<Current>> = MutableLiveData()

    fun getCurrentWeatherData(location : String) = viewModelScope.launch {
        try {
            currentWeatherLiveData.postValue(getCurrentWeatherUseCase.execute(location))
        }catch (ex : Exception){
            Log.d("WEATHER VIEW MODEL" , "Error = " + ex.message)
        }
    }
}