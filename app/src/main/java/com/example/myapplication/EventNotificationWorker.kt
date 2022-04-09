package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.NotificationReceiver.Companion.EVENT_ID_KEY
import com.example.myapplication.NotificationReceiver.Companion.EVENT_NAME_KEY
import com.example.myapplication.NotificationReceiver.Companion.SUM_KEY
import com.example.myapplication.presentation.view.event.EventDetailActivity
import com.example.myapplication.presentation.view.event.EventDetailActivity.Companion.EVENT_KEY

class EventNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val NOTIFICATION_TEXT = "Спасибо, что пожертвовали %s ₽! Будем " +
            "очень признательны, если вы сможете пожертвовать еще больше."
        const val HIDDEN_NOTIFICATION_TEXT = "Напоминаем, что мы будем очень признательны, если " +
            "вы сможете пожертвовать еще больше."
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "2"
        const val CHANNEL_NAME = "event_notification_chanel"
    }

    private val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    )

    override fun doWork(): Result {
        val eventName = inputData.getString(EVENT_NAME_KEY)
        val eventId = inputData.getLong(EVENT_ID_KEY, 0)
        val moneySum = inputData.getInt(SUM_KEY, 0)

        sendNotification(eventId, eventName, moneySum)

        return Result.success()
    }

    private fun sendNotification(eventId: Long, eventName: String?, moneySum: Int) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(eventName)
            .setContentText(NOTIFICATION_TEXT.format(moneySum))
            .setAutoCancel(true)
            .setContentIntent(createIntentContent(eventId))
            .setSmallIcon(R.drawable.icon_logo)
            .addAction(
                R.drawable.ic_icon_calendar_grey,
                "Напомнить позже",
                createIntentAction(eventId, eventName, moneySum)
            )

        with(NotificationManagerCompat.from(applicationContext)) {
            createNotificationChannel(channel)
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createIntentAction(eventId: Long, eventName: String?, moneySum: Int): PendingIntent {
        val remindLaterIntent = Intent(applicationContext, LaterNotificationReceiver::class.java)
        remindLaterIntent.putExtra(EVENT_ID_KEY, eventId)
        remindLaterIntent.putExtra(EVENT_NAME_KEY, eventName)
        remindLaterIntent.putExtra(SUM_KEY, moneySum)

        return PendingIntent.getBroadcast(
            applicationContext, 0, remindLaterIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createIntentContent(eventId: Long): PendingIntent {
        val openEventIntent = Intent(applicationContext, EventDetailActivity::class.java)
        openEventIntent.putExtra(EVENT_KEY, eventId)
        return PendingIntent.getActivity(
            applicationContext, 0, openEventIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }
}
