package com.techafresh.skycast.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.frogobox.notification.FrogoNotification

class SendNotifWorker(
    private val appContext : Context,
    private val parameters: WorkerParameters
) : Worker(appContext , parameters){
    override fun doWork(): Result {
        val sharedPreferences = appContext.getSharedPreferences("getdataworker", Context.MODE_PRIVATE) ?: return Result.failure()
        val title = sharedPreferences.getString("title" , "Header") ?: "Title"
//        val contentText = sharedPreferences.getString("content_text" , "Big Text") ?: "Content Text"
        val content2 = sharedPreferences.getString("debug" , "Sub Text") ?: "Sub Text"
        val subText = sharedPreferences.getString("sub_text" , "Sub Text") ?: "Sub Text"
        sendNotification(
            appContext,
            channelId = "Channel 01",
            channelName = "Weather Notification Periodic",
            title = title,
            contentText = content2,
            subText = subText,
            importance = NotificationManager.IMPORTANCE_MAX
        )
        return Result.success()
    }

    private fun sendNotification(
        context: Context,
        channelId : String,
        channelName : String,
        title : String,
        contentText : String,
        subText : String,
        importance : Int,
        smallIcon : Int = formatImage(1204, 1),
        largeIcon : Int = formatImage(1000, 1),
//        intent: PendingIntent
    ){
        FrogoNotification.Inject(context)
            .setChannelId(channelId)
            .setChannelName(channelName)
//            .setContentIntent(intent)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSubText(subText)
            .setupAutoCancel()
            .build()
            .launch(importance)
    }
}