package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.NotificationReceiver.Companion.EVENT_ID_KEY
import com.example.myapplication.NotificationReceiver.Companion.EVENT_NAME_KEY
import com.example.myapplication.NotificationReceiver.Companion.SUM_KEY

class LaterNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val DELAY_MILLISECONDS = 1800000
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId = intent?.getLongExtra(EVENT_ID_KEY, 0)
        val eventName = intent?.getStringExtra(EVENT_NAME_KEY)
        val moneySum = intent?.getIntExtra(SUM_KEY, 0)

        sendNotification(context, eventId, eventName, moneySum)
    }

    private fun sendNotification(context: Context?, eventId: Long?, eventName: String?, moneySum: Int?) {
        val alarmManager: AlarmManager? = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val futureInMillis = SystemClock.elapsedRealtime() + DELAY_MILLISECONDS

        with(NotificationManagerCompat.from(context!!)) {
            cancel(EventNotificationWorker.NOTIFICATION_ID)
        }
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis,
            createIntentContent(context, eventId, eventName, moneySum)
        )
    }

    private fun createIntentContent(
        context: Context?,
        eventId: Long?,
        eventName: String?,
        moneySum: Int?
    ): PendingIntent {
        val remindLaterIntent = Intent(context, NotificationReceiver::class.java)
        remindLaterIntent.putExtra(EVENT_ID_KEY, eventId)
        remindLaterIntent.putExtra(EVENT_NAME_KEY, eventName)
        remindLaterIntent.putExtra(SUM_KEY, moneySum)
        return PendingIntent.getBroadcast(
            context, 0, remindLaterIntent, PendingIntent.FLAG_IMMUTABLE
        )
    }
}
