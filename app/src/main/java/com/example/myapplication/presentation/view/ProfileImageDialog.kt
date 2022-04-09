package com.example.myapplication.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.presentation.view.ProfileFragment.Companion.Flag as Flag

class ProfileImageDialog : DialogFragment() {

    private val items = arrayListOf(
        R.string.upload_photo_profile,
        R.string.make_photo_profile,
        R.string.delete_photo_profile
    )
    private val images = arrayListOf(
        R.drawable.ic_upload,
        R.drawable.ic_camera,
        R.drawable.ic_delete
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val actions = arrayListOf(
            Flag.ChooseImageFlag(),
            Flag.CaptureImageFlag(),
            Flag.DeleteImageFlag()
        )

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_profile_image_dialog, null)
            val listView = view.findViewById<ListView>(R.id.profile_image_dialog_listview)
            val adapter = DialogAdapter(
                items, images, actions, this::passDataToIntentAndFinish
            )
            listView.adapter = adapter
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun passDataToIntentAndFinish(flag: Flag) {
        parentFragmentManager.setFragmentResult(
            ProfileFragment.imageRequestKey, bundleOf(Pair(ProfileFragment.imageRequestKey, flag.key))
        )
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}
