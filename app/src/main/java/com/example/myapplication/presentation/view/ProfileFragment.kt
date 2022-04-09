package com.example.myapplication.presentation.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    companion object {
        const val imageRequestKey = "result"
        const val imageDialogTag = "dialog"
        const val resultData = "data"
        const val imageIntentType = "image/*"

        sealed class Flag {
            abstract val key: String

            data class ChooseImageFlag(override val key: String = "profileChooseImage") : Flag()
            data class CaptureImageFlag(override val key: String = "profileTakeImage") : Flag()
            data class DeleteImageFlag(override val key: String = "profileDeleteImage") : Flag()
        }
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root

        (activity as AppCompatActivity).let {
            it.setSupportActionBar(binding.toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            it.supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        loadFriendImage(R.drawable.avatar_1, binding.friend1Photo)
        loadFriendImage(R.drawable.avatar_2, binding.friend2Photo)
        loadFriendImage(R.drawable.avatar_3, binding.friend3Photo)

        binding.manImage.setOnClickListener {
            val dialog = ProfileImageDialog()

            childFragmentManager.setFragmentResultListener(
                imageRequestKey, dialog
            ) { requestKey, bundle ->
                bundle.getString(requestKey)?.let { it1 ->
                    val flag = when (it1) {
                        Flag.CaptureImageFlag().key -> Flag.CaptureImageFlag()
                        Flag.DeleteImageFlag().key -> Flag.DeleteImageFlag()
                        Flag.ChooseImageFlag().key -> Flag.ChooseImageFlag()
                        else -> return@let
                    }
                    dialogFinished(flag)
                }
            }
            dialog.show(childFragmentManager, imageDialogTag)
        }
        return view
    }

    private fun loadFriendImage(drawableId: Int, imageView: ImageView) {
        Glide.with(this).load(drawableId)
            .placeholder(R.drawable.avatar_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    private fun dialogFinished(key: Flag) {
        when (key) {
            is Flag.CaptureImageFlag -> dispatchTakePictureIntent()
            is Flag.DeleteImageFlag -> deleteImage()
            is Flag.ChooseImageFlag -> dispatchChoosePictureIntent()
        }
    }

    private fun deleteImage() {
        binding.manImage.setImageDrawable(null)
    }

    private val takeImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get(resultData) as Bitmap
            binding.manImage.setImageBitmap(imageBitmap)
        }
    }

    private val chooseImageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.data == null) {
                return@registerForActivityResult
            }
            val inputStream = requireContext().contentResolver.openInputStream(data.data!!)
            binding.manImage.setImageBitmap(BitmapFactory.decodeStream(inputStream))
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takeImageResultLauncher.launch(takePictureIntent)
    }
    private fun dispatchChoosePictureIntent() {
        val choosePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        choosePictureIntent.type = imageIntentType
        chooseImageResultLauncher.launch(choosePictureIntent)
    }
}
