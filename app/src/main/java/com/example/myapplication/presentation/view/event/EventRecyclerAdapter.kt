package com.example.myapplication.presentation.view.event

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.event.Event
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class EventRecyclerAdapter(
    var eventDetailLauncher: ActivityResultLauncher<Intent>?
) : ListAdapter<Event, EventRecyclerAdapter.EventViewHolder>(DiffCallback()) {

    companion object {
        const val DESCRIPTION_MAX_LENGTH = 50
        const val NANO_OF_SECOND = 1000
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var imageView: ImageView? = null
        var infoTextView: TextView? = null
        var dateTextView: TextView? = null
        var layout: ConstraintLayout? = null

        init {
            titleTextView = itemView.findViewById(R.id.newsTitleTextView)
            imageView = itemView.findViewById(R.id.newsImageView)
            infoTextView = itemView.findViewById(R.id.newsInfoTextView)
            dateTextView = itemView.findViewById(R.id.newsDateTextView)
            layout = itemView.findViewById(R.id.eventLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_card, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = getItem(position)
        holder.titleTextView?.text = item.name
        holder.infoTextView?.text = "%s...".format(item.description.take(DESCRIPTION_MAX_LENGTH))
        val date1 = LocalDateTime.ofEpochSecond(
            item.startDate.toLong(),
            NANO_OF_SECOND,
            OffsetDateTime.now().offset
        )
        val date2 = LocalDateTime.ofEpochSecond(
            item.endDate.toLong(),
            NANO_OF_SECOND,
            OffsetDateTime.now().offset
        )
        val formatter = DateTimeFormatter.ofPattern(EventDetailActivity.DATE_PATTERN)
        val dateInfo = EventDetailActivity.DATE_INFO_FORMAT.format(date1.format(formatter), date2.format(formatter))
        holder.dateTextView?.text = dateInfo
        setEventImage(holder, item.photos)
        holder.layout?.setOnClickListener {
            val intent = Intent(holder.itemView.context, EventDetailActivity::class.java)

            intent.putExtra(EventDetailActivity.EVENT_KEY, item.id)

            eventDetailLauncher?.launch(intent)
        }
    }

    private fun setEventImage(
        holder: EventViewHolder,
        itemPhotos: ArrayList<String>
    ) {
        if (itemPhotos.isNotEmpty()) {
            holder.imageView?.setImageResource(
                holder.itemView.context.resources.getIdentifier(
                    itemPhotos[0],
                    "drawable",
                    holder.itemView.context.packageName
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Event, newItem: Event) =
        oldItem == newItem
}
