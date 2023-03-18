package com.techafresh.skycast.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.techafresh.skycast.data.dataClasses.forecast.Forecast
import com.techafresh.skycast.data.dataSources.local.LocalDataSource
import com.techafresh.skycast.data.dataSources.remote.RemoteDataSource
import com.techafresh.skycast.data.retrofit.api.WeatherApi
import com.techafresh.skycast.domain.usecases.GetWeatherForecastUseCase
import com.techafresh.skycast.domain.usecases.SaveForecastUseCase
import com.techafresh.skycast.presentation.viewmodel.WeatherViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltWorker
class DownloadJsonDataToDBWorker @AssistedInject constructor(
    @Assisted context : Context,
    @Assisted params : WorkerParameters,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CoroutineWorker(context , params) {
    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to **synchronously** do your work and return the
     * [androidx.work.ListenableWorker.Result] from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * [ListenableWorker].
     *
     *
     * A Worker has a well defined
     * [execution window](https://d.android.com/reference/android/app/job/JobScheduler)
     * to finish its execution and return a [androidx.work.ListenableWorker.Result].  After
     * this time has expired, the Worker will be signalled to stop.
     *
     * @return The [androidx.work.ListenableWorker.Result] of the computation; note that
     * dependent work will not execute if you use
     * [androidx.work.ListenableWorker.Result.failure] or
     * [androidx.work.ListenableWorker.Result.failure]
     */

//    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
//    private val saveForecastUseCase: SaveForecastUseCase,

//    @Inject lateinit var getWeatherForecastUseCase: GetWeatherForecastUseCase
//    @Inject lateinit var saveForecastUseCase: SaveForecastUseCase

    /**
     * A suspending method to do your work.
     * <p>
     * To specify which [CoroutineDispatcher] your work should run on, use `withContext()`
     * within `doWork()`.
     * If there is no other dispatcher declared, [Dispatchers.Default] will be used.
     * <p>
     * A CoroutineWorker is given a maximum of ten minutes to finish its execution and return a
     * [ListenableWorker.Result].  After this time has expired, the worker will be signalled to
     * stop.
     *
     * @return The [ListenableWorker.Result] of the result of the background work; note that
     * dependent work will not execute if you return [ListenableWorker.Result.failure]
     */
    override suspend fun doWork(): Result {

        Log.d("WORKER = " , "DownloadJsonDataToDBWorker Started.....")
//        "Trying 2 inject remotedatasourcee is null"
//        val gson = Gson()
//        val forecastString = inputData.getString("forecastDataFromViewModel")
//        val forecast : Forecast = gson.fromJson(forecastString , Forecast::class.java)

        val location = inputData.getString("mainActivityLocation")

        if (location != null){
            Log.d("WORKER Location" , "$location")
        }else{
            Log.d("WORKER Location" , "Location is Null")
        }


        return try{
            if (location != null) {
                Log.d("WORKER " , "Working")
                // Get Data From API
                val forecastData : Response<Forecast> = remoteDataSource.getWeatherForecast(location)

                val forecast : Forecast = forecastData.body()!!

                Log.d("WORKER FORECAST = " , forecast.toString())

                // Save To DB
//                localDataSource.deleteAllForecast()
                forecast.date = DateFormat.getDateTimeInstance().format(Date())
                localDataSource.saveForecast(forecast)
            }
            Result.success()
        }catch (ex : Exception){
            Log.d("WORKER ERROR = " , "${ex.message}")
            Result.retry()
        }

    }
    }
