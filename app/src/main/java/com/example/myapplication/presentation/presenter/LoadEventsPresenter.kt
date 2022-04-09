package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.view.event.LoadEventsView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class LoadEventsPresenter : MvpPresenter<LoadEventsView>() {

    var allEvents: List<Event>? = null

    fun getAllEventsData(repository: AppRepository) {
        runBlocking(Dispatchers.IO) {
            repository.populateDatabase()
        }
        presenterScope.launch {
            allEvents = repository.getAllEvents().toList()
            viewState.getAllEventsData(ArrayList(allEvents))
        }
    }

    fun updateBadge(repository: AppRepository) {
        presenterScope.launch {
            viewState.updateBadge(ArrayList(allEvents))
        }
    }
}
