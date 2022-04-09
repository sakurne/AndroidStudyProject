package com.example.myapplication.data.repositories

import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.network.RetrofitServices
import com.example.myapplication.domain.event.Event
import com.example.myapplication.domain.eventCategory.EventCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
    database: AppDatabase,
    private val retrofitServices: RetrofitServices,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val eventCategoryDao = database.eventCategoryDao()
    private val eventDao = database.eventDao()

    suspend fun getAllEvents(): List<Event> = withContext(defaultDispatcher) {
        return@withContext eventDao.getAll()
    }

    suspend fun getAllEventCategories(): List<EventCategory> = withContext(defaultDispatcher) {
        return@withContext eventCategoryDao.getAll()
    }

    suspend fun getEventById(id: Long): Event? = withContext(defaultDispatcher) {
        return@withContext eventDao.getEvent(id)
    }

    suspend fun populateDatabase() = withContext(defaultDispatcher) {
        eventDao.deleteAll()
        eventCategoryDao.deleteAll()

        val loadedEvents = retrofitServices.loadEvents()
        for (event: Event in loadedEvents) {
            eventDao.insert(event)
        }

        val loadedEventCategories = retrofitServices.loadEventCategories()
        for (eventCategory: EventCategory in loadedEventCategories) {
            eventCategoryDao.insert(eventCategory)
        }
    }
}
