package com.techafresh.skycast.domain.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object Utils {

    fun setAlarm(context : Context, timeToRing : Long , alarmIntent : Intent){
        Log.d("MYTAG GetTime" ,"INSIDE THE Schedule Notification Method")

        val intent = Intent(context , NotificationReceiver::class.java)
        intent.putExtra("day" , "Today")
        intent.putExtra("iconCode" , 1063)
        intent.putExtra("isDay" , 1)
        intent.putExtra("userLocation" , "Ibadan")
        intent.putExtra("temp_c" , "30")
        intent.putExtra("temp_f" , "26")
        intent.putExtra("condition" , "Terrible Rain")
        intent.putExtra("check" , "Message Received")


        val forecastIntent = PendingIntent.getBroadcast(
            context,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < timeToRing){
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                timeToRing,
                1000 * 60 * 2,
                forecastIntent
            )
        }


    }
}