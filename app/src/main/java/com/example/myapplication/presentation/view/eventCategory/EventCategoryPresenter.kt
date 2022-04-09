package com.example.myapplication.presentation.view.eventCategory

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.domain.eventCategory.EventCategory
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class EventCategoryPresenter : MvpPresenter<EventCategoryFilterView>() {

    var allEventCategories: List<EventCategory>? = null

    fun showCategories(
        repository: AppRepository
    ) {
        presenterScope.launch {
            allEventCategories = repository.getAllEventCategories()
            allEventCategories?.let {
                viewState.showCategories(allEventCategories!!)
            }
        }
    }
}
