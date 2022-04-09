package com.example.myapplication.presentation.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.myapplication.R
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerRepositoryComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.presenter.EventsSearchPresenter
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class SearchFragment : MvpAppCompatFragment(), EventsSearchView {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var repository: AppRepository

    @InjectPresenter
    lateinit var presenter: EventsSearchPresenter

    companion object {
        const val SEARCH_STRING_KEY = "searchString"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_STRING_KEY, binding.searchView.query.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerRepositoryComponent.builder()
            .appModule(AppModule(requireActivity().application))
            .databaseModule(DatabaseModule()).build()
            .inject(this)

        binding = FragmentSearchBinding.inflate(layoutInflater)
        val adapter = SearchViewPagerAdapter(
            this, binding.searchTabLayout.tabCount, null
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.searchTabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.search_tab_1)
                1 -> resources.getString(R.string.search_tab_2)
                else -> ""
            }
        }.attach()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    presenter.filterByName(repository, newText)
                }
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        return binding.root
    }

    override fun showEvents(events: ArrayList<Event>?) {
        val eventsAdapter = SearchViewPagerAdapter(
            this,
            binding.searchTabLayout.tabCount,
            events?.map { it.name }
        )
        binding.viewPager.adapter = eventsAdapter
    }
}
