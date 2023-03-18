package com.techafresh.skycast

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp : Application() , Configuration.Provider{

    @Inject lateinit var workerFactory: HiltWorkerFactory

    /**
     * @return The [Configuration] used to initialize WorkManager
     */
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()


}