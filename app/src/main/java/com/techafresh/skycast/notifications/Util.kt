package com.techafresh.skycast.notifications

import android.app.NotificationManager
import android.content.Context
import com.techafresh.skycast.R
import de.coldtea.smplr.smplralarm.alarmNotification
import de.coldtea.smplr.smplralarm.channel
import de.coldtea.smplr.smplralarm.smplrAlarmSet

fun sendForecastNotification(
    context: Context,
    hour : Int,
    minute: Int,
    title : String,
    bigText : String,
    icon : Int
){
    smplrAlarmSet(context) {
        hour { hour }
        min { minute }
        weekdays {
            monday()
            tuesday()
            wednesday()
            thursday()
            friday()
            saturday()
            sunday()
        }
        notification {
            alarmNotification {
                smallIcon { icon }
//                message { "Yooo0000000000000000000000000000" }
                title { title }
                bigText { bigText }
            }
        }
        notificationChannel {
            channel {
                importance { NotificationManager.IMPORTANCE_MAX }
                showBadge { false }
                name { "Weather Forecast Notification BY 8AM" }
                description { "This notification channel is created by Techafresh" }
            }
        }
    }
}

fun formatImage(iconCode : Int , isDay : Int) : Int{
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

        else -> R.drawable.group16
    }
}