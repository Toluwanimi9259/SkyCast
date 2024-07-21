package com.techafresh.skycast.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.techafresh.skycast.R

class SendNotifWorkerAlarm (
    private val appContext : Context,
    private val parameters: WorkerParameters
) : Worker(appContext , parameters){
    override fun doWork(): Result {
        val sharedPreferences = appContext.getSharedPreferences("getdataworker", Context.MODE_PRIVATE) ?: return Result.failure()
        val title = sharedPreferences.getString("title" , "Header") ?: "Title"
        val contentText = sharedPreferences.getString("content_text" , "Big Text") ?: "Content Text"
//        val content2 = sharedPreferences.getString("debug" , "Sub Text") ?: "Sub Text"
        val icon = sharedPreferences.getString("icon", "400") ?: "400"
        val image = if (icon == "400") R.drawable.ic_launcher_foreground else formatImage(icon.split('#')[0].toInt(), icon.split('#')[1].toInt())

        sendForecastNotification(
            applicationContext,
            hour = 8,
            minute = 0,
            title = title,
            bigText = contentText,
            icon = image
        )

        return Result.success()
    }
}