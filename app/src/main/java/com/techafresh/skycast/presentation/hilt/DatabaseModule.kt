package com.techafresh.skycast.presentation.hilt

import android.app.Application
import androidx.room.Room
import com.techafresh.skycast.data.dataClasses.forecast.Day
import com.techafresh.skycast.data.room.dao.DayDao
import com.techafresh.skycast.data.room.db.ForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideForecastDatabase(application: Application) : ForecastDatabase{
        return Room.databaseBuilder(
            application ,
            ForecastDatabase::class.java ,
            "forecast_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDayDao(db: ForecastDatabase) : DayDao{
        return db.dao()
    }
}