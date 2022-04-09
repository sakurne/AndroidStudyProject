package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.view.event.EventsView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class EventsPresenter : MvpPresenter<EventsView>() {

    var allEvents: List<Event>? = null

    fun showEvents(repository: AppRepository) {
        presenterScope.launch() {
            allEvents = repository.getAllEvents().toList()
            if (!allEvents.isNullOrEmpty()) {
                viewState.showEvents(ArrayList(allEvents))
            }
        }
    }

    fun filterByCategories(ids: ArrayList<Long>) {
        presenterScope.launch {
            val eventsFiltered = ArrayList(allEvents?.filter { it.category.toLong() in ids })
            viewState.filterByCategories(eventsFiltered)
        }
    }
}
