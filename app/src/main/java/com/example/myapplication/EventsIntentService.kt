package com.example.myapplication

import android.app.IntentService
import android.content.Intent
import android.os.Parcelable

class EventsIntentService(name: String = "eventsIntent") : IntentService(name) {
    companion object {
        const val ACTION_MYINTENTSERVICE = "com.example.myapplication.intentservice.RESPONSE"
    }

    override fun onHandleIntent(intent: Intent?) {
        val resultIntent = Intent()
        resultIntent.putExtra(
            "events",
            JSONEventHelper.importFromJSON(applicationContext) as ArrayList<Parcelable>
        )
        resultIntent.action = ACTION_MYINTENTSERVICE
        resultIntent.addCategory(Intent.CATEGORY_DEFAULT)
        sendBroadcast(resultIntent)
    }
}
