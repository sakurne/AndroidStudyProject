package com.example.myapplication.presentation.view.event

import com.example.myapplication.domain.event.Event
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface EventsSearchView : MvpView {

    @AddToEndSingle
    fun showEvents(events: ArrayList<Event>?)
}
