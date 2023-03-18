package com.techafresh.skycast.domain.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.TIME_SET"){
            // ideally we should be fetching the data from a database
            val sharedPref = context?.getSharedPreferences("First_Timer_Checker", Context.MODE_PRIVATE) ?: return
            val timeToRing = sharedPref.getLong("timeToRing", 1)
//            Utils.setAlarm(context, timeToRing)
        }
    }
}