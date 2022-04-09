package com.example.myapplication.presentation.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerRepositoryComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.presenter.LoadEventsPresenter
import com.example.myapplication.presentation.view.event.EventsFragment
import com.example.myapplication.presentation.view.event.LoadEventsView
import com.example.myapplication.presentation.view.event.SearchFragment
import com.google.android.material.navigation.NavigationBarView
import com.jakewharton.threetenabp.AndroidThreeTen
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), LoadEventsView {

    @Inject
    lateinit var repository: AppRepository

    @InjectPresenter
    lateinit var presenter: LoadEventsPresenter

    companion object {
        const val APP_PREFERENCES = "settings"
        const val READ_EVENTS_IDS = "read_events"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        DaggerRepositoryComponent.builder()
            .appModule(AppModule(application))
            .databaseModule(DatabaseModule()).build()
            .inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.menu.getItem(1).isChecked = true
        openFragment(SearchFragment())

        (binding.navView as NavigationBarView).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    openFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_help -> {
                    openFragment(HelpFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_search -> {
                    openFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_news -> {
                    openFragment(EventsFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        presenter.getAllEventsData(repository)
    }

    override fun getAllEventsData(events: ArrayList<Event>) {
        updateBadge(events)
    }

    override fun updateBadge(events: ArrayList<Event>) {
        setBadgeNumber((events.map { event -> event.id.toString() }).toSet())
    }

    private fun setBadgeNumber(allEventsIds: Set<String>) {
        val settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val readMessages = settings.getStringSet(READ_EVENTS_IDS, null)
        val badgeNum = if (readMessages == null) {
            allEventsIds.count()
        } else {
            (allEventsIds subtract readMessages).count()
        }
        if (badgeNum > 0) {
            binding.navView.getOrCreateBadge(R.id.navigation_news).number = badgeNum
        } else {
            binding.navView.removeBadge(R.id.navigation_news)
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainFragment.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    val eventDetailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            presenter.updateBadge(repository)
        }
    }
}
