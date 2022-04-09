package com.example.myapplication

import android.content.Context
import com.example.myapplication.domain.eventCategory.EventCategory
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

class JSONEventCategoryHelper {
    companion object {
        private const val FILE_NAME = "event_categories.json"

        fun importFromJSON(context: Context): List<EventCategory>? {
            try {

                context.resources.assets.open(FILE_NAME).use { fileInputStream ->
                    InputStreamReader(fileInputStream).use { streamReader ->
                        val gson = Gson()
                        val dataItems = gson.fromJson(streamReader, DataItems::class.java)
                        return dataItems.eventCategories
                    }
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return null
        }
    }

    private class DataItems {
        var eventCategories: List<EventCategory>? = null
    }
}
