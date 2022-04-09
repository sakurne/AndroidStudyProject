package com.example.myapplication.presentation.view.event

import com.example.myapplication.domain.event.Event
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface EventDetailView : MvpView {

    @AddToEndSingle
    fun showEventInfo(event: Event)
}
