package com.example.myapplication.presentation.view.event

import com.example.myapplication.domain.event.Event
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface LoadEventsView : MvpView {

    @AddToEndSingle
    fun getAllEventsData(events: ArrayList<Event>)

    @AddToEndSingle
    fun updateBadge(events: ArrayList<Event>)
}
