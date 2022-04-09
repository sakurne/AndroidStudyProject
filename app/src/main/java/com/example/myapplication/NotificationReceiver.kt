package com.example.myapplication

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.presentation.view.event.EventDetailActivity

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val LATER_NOTIFICATION_ID = 2
        const val SUM_KEY = "sum"
        const val EVENT_NAME_KEY = "event_name"
        const val EVENT_ID_KEY = "event_id"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId = intent?.extras?.getLong(EVENT_ID_KEY)
        val eventName = intent?.extras?.getString(EVENT_NAME_KEY)

        sendNotification(context, eventId, eventName)
    }

    private fun createIntentContent(context: Context?, eventId: Long?): PendingIntent {
        val openEventIntent = Intent(context, EventDetailActivity::class.java)
        openEventIntent.putExtra(EventDetailActivity.EVENT_KEY, eventId)
        return PendingIntent.getActivity(
            context, 0, openEventIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun sendNotification(context: Context?, eventId: Long?, eventName: String?) {
        val secondNotificationBuilder = Notification.Builder(
            context,
            EventNotificationWorker.CHANNEL_ID
        )
            .setContentTitle(eventName)
            .setContentText(EventNotificationWorker.HIDDEN_NOTIFICATION_TEXT)
            .setContentIntent(createIntentContent(context, eventId))
            .setSmallIcon(R.drawable.icon_logo)

        with(NotificationManagerCompat.from(context!!)) {
            notify(LATER_NOTIFICATION_ID, secondNotificationBuilder.build())
        }
    }
}
