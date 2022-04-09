package com.example.myapplication

import android.content.Context
import com.example.myapplication.domain.event.Event
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

class JSONEventHelper {
    companion object {
        private const val FILE_NAME = "events.json"

        fun importFromJSON(context: Context): List<Event>? {
            try {
                context.resources.assets.open(FILE_NAME).use { fileInputStream ->
                    InputStreamReader(fileInputStream).use { streamReader ->
                        val gson = Gson()
                        val dataItems = gson.fromJson(streamReader, DataItems::class.java)
                        return dataItems.events
                    }
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return null
        }
    }

    private class DataItems {
        var events: List<Event>? = null
    }
}
