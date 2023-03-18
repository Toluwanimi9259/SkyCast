package com.techafresh.skycast.domain.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.techafresh.skycast.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(mContext: Context?, intent: Intent?) {
            Log.d("MYTAG RECEIVER" ,"INSIDE THE RECEIVER")

            val notificationManager = mContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val day = intent?.getStringExtra("day")
            val iconCode = intent?.getIntExtra("iconCode" ,100)
            val isDay = intent?.getIntExtra("isDay" , 1)
            val userLocation = intent?.getStringExtra("userLocation")
            val temp_c = intent?.getStringExtra("temp_c")
            val temp_f = intent?.getStringExtra("temp_f")
            val condition = intent?.getStringExtra("condition")
            val message = intent?.getStringExtra("check")

        Log.d("MYTAG RECEIVER" ,"Message === $message")

            val channelID = "100"
            val channelName = "Forecast"
            val channelDescription = "Forecast Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Notification Channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelID, channelName, importance).apply {
                    description = channelDescription
                }
                notificationManager.createNotificationChannel(channel)
            }

            // converting a jpeg file to Bitmap file and making an instance of Bitmap!
            val imgBitmap= BitmapFactory.decodeResource(mContext.resources,formatImage(iconCode!! , isDay!!))

            val notificationID = 45

            // Building notification
            val nBuilder= NotificationCompat.Builder(mContext,channelID)
                .setContentTitle("$day in $userLocation: $condition")
                .setContentText("$temp_c°/$temp_f° See full forecast")
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // passing the Bitmap object as an argument
                .setLargeIcon(imgBitmap)
                // Expandable notification
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(imgBitmap)
                    // as we pass null in bigLargeIcon() so the large icon
                    // will goes away when the notification will be expanded.
                    .bigLargeIcon(null))
                .build()

            notificationManager.notify(notificationID , nBuilder)
    }

    private fun formatImage(iconCode : Int , isDay : Int) : Int{
        Log.d("MYTAG RECEIVER" ,"INSIDE THE Format Image MEthod")
        return when(iconCode){
            1000 -> return if (isDay == 1){
                R.drawable.daysun
            }else {
                R.drawable.nightmoon
            }

            1003 -> return if (isDay == 1){
                R.drawable.dayclouds
            }else {
                R.drawable.nightclouds
            }

            1006 -> return if (isDay == 1){
                R.drawable.dayclouds
            }else {
                R.drawable.nightclouds
            }

            1009 -> return if (isDay == 1){
                R.drawable.overcast
            }else {
                R.drawable.darkcloud
            }

            1030 -> return if (isDay == 1){
                R.drawable.group37
            }else {
                R.drawable.group37
            }

            1063 -> return if (isDay == 1){
                R.drawable.daysrain
            }else {
                R.drawable.nightrain
            }

            1066 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1069 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1072 -> return if (isDay == 1){
                R.drawable.group17
            }else {
                R.drawable.group17
            }

            1087 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.daystorm
            }

            1114 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.daystorm
            }

            1117 -> return if (isDay == 1){
                R.drawable.group17
            }else {
                R.drawable.group17
            }

            1135 -> return if (isDay == 1){
                R.drawable.cglousd
            }else {
                R.drawable.darkcloud
            }

            1147 -> return if (isDay == 1){
                R.drawable.cglousd
            }else {
                R.drawable.darkcloud
            }

            1150 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1153 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1168 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1171 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1180 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1183 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1186 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1189 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1192 -> return if (isDay == 1){
                R.drawable.group50
            }else {
                R.drawable.group17
            }

            1195 -> return if (isDay == 1){
                R.drawable.group50
            }else {
                R.drawable.group17
            }

            1198 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1201 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1204 -> return if (isDay == 1){
                R.drawable.group47
            }else {
                R.drawable.group47
            }

            1207 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1210 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1213 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1216 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1219 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1222 -> return if (isDay == 1){
                R.drawable.group44
            }else {
                R.drawable.group43
            }

            1225 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1237 -> return if (isDay == 1){
                R.drawable.snow
            }else {
                R.drawable.snow
            }

            1240 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1243 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1246 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1249 -> return if (isDay == 1){
                R.drawable.rain
            }else {
                R.drawable.rain
            }

            1252 -> return if (isDay == 1){
                R.drawable.group23
            }else {
                R.drawable.group23
            }

            1255 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1258 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1261 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1264 -> return if (isDay == 1){
                R.drawable.group43
            }else {
                R.drawable.group43
            }

            1273 -> return if (isDay == 1){
                R.drawable.group42
            }else {
                R.drawable.group42
            }

            1276 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.nightstorm
            }

            1279 -> return if (isDay == 1){
                R.drawable.daysnow
            }else {
                R.drawable.nightsnow
            }

            1282 -> return if (isDay == 1){
                R.drawable.daystorm
            }else {
                R.drawable.nightstorm
            }

            else -> R.drawable.fetcher
        }
    }

}