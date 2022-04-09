package com.example.myapplication.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class HelpRecyclerAdapter(val context: Context, private val names: List<Int>, private val images: List<Int>) :
    RecyclerView.Adapter<HelpRecyclerAdapter.HelpViewHolder>() {

    class HelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var imageView: ImageView? = null

        init {
            textView = itemView.findViewById(R.id.help_card_text)
            imageView = itemView.findViewById(R.id.help_card_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val itemView =
            LayoutInflater.from(context)
                .inflate(R.layout.help_card, parent, false)
        return HelpViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.textView?.setText(names[position])
        holder.imageView?.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }
}
