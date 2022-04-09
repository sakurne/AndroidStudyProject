package com.example.myapplication.presentation.view.event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerRepositoryComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.presenter.EventsPresenter
import com.example.myapplication.presentation.view.MainActivity
import com.example.myapplication.presentation.view.eventCategory.EventsFilterActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import kotlin.collections.ArrayList

class EventsFragment : MvpAppCompatFragment(), EventsView {

    private lateinit var binding: FragmentNewsBinding

    @Inject
    lateinit var repository: AppRepository

    @InjectPresenter
    lateinit var presenter: EventsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerRepositoryComponent.builder()
            .appModule(AppModule(requireActivity().application))
            .databaseModule(DatabaseModule()).build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.filterButton.setOnClickListener {
            val intent = Intent(requireContext(), EventsFilterActivity::class.java)
            filtersLauncher.launch(intent)
        }
        val adapter = EventRecyclerAdapter((activity as MainActivity).eventDetailLauncher)
        val recyclerView = binding.eventsReecyclerViee
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
        binding.eventsLoadIndicator.visibility = View.VISIBLE
        presenter.showEvents(repository)
        return binding.root
    }

    private fun onPostExecute(result: List<Event>?) {
        (binding.eventsReecyclerViee.adapter as EventRecyclerAdapter).submitList(result)
        binding.eventsLoadIndicator.visibility = View.INVISIBLE
    }

    private val filtersLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.extras == null) {
                return@registerForActivityResult
            }
            presenter.filterByCategories(
                ArrayList(data.getStringArrayListExtra("filtered")?.map { it.toLong() })
            )
        }
    }

    private fun bindAdapter(events: List<Event>?) {
        (binding.eventsReecyclerViee.adapter as EventRecyclerAdapter).submitList(events)
    }

    override fun showEvents(events: ArrayList<Event>) {
        onPostExecute(events)
    }

    override fun filterByCategories(events: ArrayList<Event>) {
        bindAdapter(events)
    }
}
