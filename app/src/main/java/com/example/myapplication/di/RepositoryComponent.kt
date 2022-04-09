package com.example.myapplication.di

import com.example.myapplication.presentation.view.MainActivity
import com.example.myapplication.presentation.view.event.EventDetailActivity
import com.example.myapplication.presentation.view.event.EventsFragment
import com.example.myapplication.presentation.view.event.SearchFragment
import com.example.myapplication.presentation.view.eventCategory.EventsFilterActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        DatabaseModule::class
    ]
)
@Singleton
interface RepositoryComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: EventsFragment)
    fun inject(presenter: EventsFilterActivity)
    fun inject(detailActivity: EventDetailActivity)
}
