package com.example.myapplication.presentation.view.eventCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.eventCategory.EventCategory

class EventCategoriesAdapter(
    private val chosenEvents: ArrayList<Long>
) :
    ListAdapter<EventCategory, EventCategoriesAdapter.EventViewHolder>(CategoriesDiffCallback()) {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsSwitch: SwitchCompat? = null

        init {
            newsSwitch = itemView.findViewById(R.id.newsSwitch)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_filter_card, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val category = getItem(position)
        holder.newsSwitch?.text = category.name
        holder.newsSwitch?.setOnCheckedChangeListener { _, isChecked ->
            val eventId = category.id
            if (isChecked && eventId !in chosenEvents) {
                chosenEvents.add(category.id)
            }
        }
    }
}

private class CategoriesDiffCallback : DiffUtil.ItemCallback<EventCategory>() {

    override fun areItemsTheSame(oldItem: EventCategory, newItem: EventCategory) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EventCategory, newItem: EventCategory) =
        oldItem == newItem
}
