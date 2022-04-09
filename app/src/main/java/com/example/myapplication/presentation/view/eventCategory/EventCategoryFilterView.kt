package com.example.myapplication.presentation.view.eventCategory

import com.example.myapplication.domain.eventCategory.EventCategory
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface EventCategoryFilterView : MvpView {

    @AddToEndSingle
    fun showCategories(eventCategories: List<EventCategory>)
}
