package com.example.myapplication.presentation.view.eventCategory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.databinding.ActivityNewsFilterBinding
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerRepositoryComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.domain.eventCategory.EventCategory
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import kotlin.collections.ArrayList

class EventsFilterActivity : MvpAppCompatActivity(), EventCategoryFilterView {

    private val chosenCategories: ArrayList<Long> = arrayListOf()
    private lateinit var binding: ActivityNewsFilterBinding

    @InjectPresenter
    lateinit var presenter: EventCategoryPresenter

    @Inject
    lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerRepositoryComponent.builder()
            .appModule(AppModule(application))
            .databaseModule(DatabaseModule()).build()
            .inject(this)

        binding = ActivityNewsFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.showCategories(repository)

        binding.filterButton.setOnClickListener {
            val intent = Intent()
            intent.putStringArrayListExtra(
                "filtered",
                ArrayList(chosenCategories.map { it.toString() })
            )
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun showCategories(eventCategories: List<EventCategory>) {
        val adapter = EventCategoriesAdapter(chosenCategories)
        val recyclerView = binding.eventCategoryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.submitList(eventCategories)
        binding.eventsLoadIndicator.visibility = View.INVISIBLE
    }
}
