package com.example.myapplication.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

class DialogAdapter(
    private val items: ArrayList<Int>,
    private val images: ArrayList<Int>,
    private val actions: ArrayList<ProfileFragment.Companion.Flag>,
    private val onClickAction: (ProfileFragment.Companion.Flag) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (parent == null) {
            return null
        }
        var dialogView = convertView
        if (dialogView == null) {
            dialogView = LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_image_dialog_item, parent, false)
        }
        dialogView!!.findViewById<TextView>(R.id.dialog_image_textview)?.text =
            parent.context.getString(items[position])
        dialogView.findViewById<ImageView>(R.id.dialog_image_icon)
            ?.setImageResource(images[position])
        dialogView.setOnClickListener {
            onClickAction(actions[position])
        }
        return dialogView
    }
}
