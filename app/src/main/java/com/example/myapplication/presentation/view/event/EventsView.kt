package com.example.myapplication.presentation.view.event

import com.example.myapplication.domain.event.Event
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface EventsView : MvpView {

    @AddToEndSingle
    fun showEvents(events: ArrayList<Event>)

    @AddToEndSingle
    fun filterByCategories(events: ArrayList<Event>)
}
