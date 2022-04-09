package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.presentation.view.event.EventDetailView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class EventDetailPresenter : MvpPresenter<EventDetailView>() {

    fun showEventInfo(repository: AppRepository, eventId: Long) {
        presenterScope.launch {
            val event = repository.getEventById(eventId)
            if (event != null) {
                viewState.showEventInfo(event)
            }
        }
    }
}
