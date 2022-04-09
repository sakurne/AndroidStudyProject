package com.example.myapplication.data.network

import com.example.myapplication.domain.event.Event
import com.example.myapplication.domain.eventCategory.EventCategory
import retrofit2.http.GET

interface RetrofitServices {
    @GET("categories")
    suspend fun loadEventCategories(): List<EventCategory>

    @GET("events")
    suspend fun loadEvents(): List<Event>
}
