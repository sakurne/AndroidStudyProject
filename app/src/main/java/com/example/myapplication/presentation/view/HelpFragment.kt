package com.example.myapplication.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class HelpFragment : Fragment() {
    private val headers = arrayListOf(
        R.string.help_item_kids,
        R.string.help_item_adults,
        R.string.help_item_elderly,
        R.string.help_item_animals,
        R.string.help_item_events
    )
    private val images = arrayListOf(
        R.drawable.kid_help,
        R.drawable.adult_help,
        R.drawable.elderly_help,
        R.drawable.animal_help,
        R.drawable.event_help
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HelpRecyclerAdapter(this.requireContext(), headers, images)
        val recyclerView = view.findViewById<RecyclerView>(R.id.help_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.adapter = adapter
    }
}
