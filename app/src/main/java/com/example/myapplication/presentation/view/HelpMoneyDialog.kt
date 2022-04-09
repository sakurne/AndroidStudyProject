package com.example.myapplication.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.DialogFragment
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.EventNotificationWorker
import com.example.myapplication.NotificationReceiver.Companion.EVENT_ID_KEY
import com.example.myapplication.NotificationReceiver.Companion.EVENT_NAME_KEY
import com.example.myapplication.NotificationReceiver.Companion.SUM_KEY
import com.example.myapplication.R
import com.example.myapplication.domain.event.Event

class HelpMoneyDialog(val event: Event) : DialogFragment() {

    companion object {
        const val MONEY_OPTION_1 = 100
        const val MONEY_OPTION_2 = 500
        const val MONEY_OPTION_3 = 1000
        const val MONEY_OPTION_4 = 2000
    }

    var chosenMoneyOption = MONEY_OPTION_2

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.help_with_money_dialog, null)
            builder.setView(view)
            builder.create()
        }
        return view ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        RectanglesBlock(dialog).bindUI()
        bindApproveButton(dialog)
        bindCancelButton(dialog)
    }

    private fun bindApproveButton(dialog: Dialog?) {
        dialog?.findViewById<Button>(R.id.buttonApprove)?.setOnClickListener {
            val data = Data.Builder()
            var sumValue = (dialog.findViewById(R.id.editTextTextSum) as EditText).text.toString()
            if (sumValue.isNotBlank()) {
                chosenMoneyOption = sumValue.toInt()
            }
            data.putString(EVENT_NAME_KEY, event.name)
            data.putLong(EVENT_ID_KEY, event.id)
            data.putInt(SUM_KEY, chosenMoneyOption)
            val workRequest = OneTimeWorkRequest.Builder(EventNotificationWorker::class.java)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresCharging(true)
                        .build()
                ).setInputData(data.build()).build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
            dialog.cancel()
        }
    }

    private fun bindCancelButton(dialog: Dialog?) {
        dialog?.findViewById<Button>(R.id.buttonCancel)?.setOnClickListener {
            dialog.cancel()
        }
    }

    data class RectangleView(val view: View?, val textView: TextView?)

    inner class RectanglesBlock(dialog: Dialog?) {

        var allRectangles: HashMap<RectangleView, Int>

        init {
            val rectangle1 = RectangleView(
                dialog?.findViewById(R.id.rectangle1),
                dialog?.findViewById(R.id.moneyText1)
            )
            val rectangle2 = RectangleView(
                dialog?.findViewById(R.id.rectangle2),
                dialog?.findViewById(R.id.moneyText2)
            )
            val rectangle3 = RectangleView(
                dialog?.findViewById(R.id.rectangle3),
                dialog?.findViewById(R.id.moneyText3)
            )
            val rectangle4 = RectangleView(
                dialog?.findViewById(R.id.rectangle4),
                dialog?.findViewById(R.id.moneyText4)
            )

            allRectangles = hashMapOf(
                rectangle1 to MONEY_OPTION_1,
                rectangle2 to MONEY_OPTION_2,
                rectangle3 to MONEY_OPTION_3,
                rectangle4 to MONEY_OPTION_4
            )
        }

        fun bindUI() {
            for (rectangle: RectangleView? in allRectangles.keys) {
                allRectangles[rectangle]?.let {
                    bindMoneyButton(rectangle, it, allRectangles.keys.toMutableSet())
                }
            }
        }

        private fun bindMoneyButton(
            rectangle: RectangleView?,
            value: Int,
            allViews: MutableSet<RectangleView?>
        ) {
            rectangle?.view?.setOnClickListener {
                chosenMoneyOption = value
                val otherViews = allViews.toMutableSet()
                otherViews.remove(rectangle)
                changeRectangleColors(rectangle, otherViews)
            }
        }

        private fun changeRectangleColors(
            highlightedView: RectangleView?,
            otherViews: Set<RectangleView?>
        ) {
            highlightedView?.view?.setBackgroundResource(R.drawable.shape_rect_money_help_highlighted)
            highlightedView?.textView?.setTextColor(getColor(resources, R.color.white, null))
            for (rectangle: RectangleView? in otherViews) {
                rectangle?.view?.setBackgroundResource(R.drawable.shape_rect_money_help)
                rectangle?.textView?.setTextColor(getColor(resources, R.color.leaf, null))
            }
        }
    }
}
