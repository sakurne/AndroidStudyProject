package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.view.event.EventsSearchView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class EventsSearchPresenter : MvpPresenter<EventsSearchView>() {

    private var allEvents: List<Event>? = null

    fun filterByName(
        repository: AppRepository,
        nameSubstring: String
    ) {
        presenterScope.launch() {
            allEvents = repository.getAllEvents()
            val result = if (nameSubstring.isNotBlank()) {
                ArrayList(allEvents?.filter { event -> event.name.contains(nameSubstring) })
            } else {
                null
            }
            viewState.showEvents(result)
        }
    }
}
