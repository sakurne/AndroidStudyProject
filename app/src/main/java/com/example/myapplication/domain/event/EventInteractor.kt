package com.example.myapplication.domain.event

interface EventInteractor {

    fun deleteAll()
    fun insert(event: Event)
}
