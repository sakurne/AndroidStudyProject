package com.example.myapplication.presentation.view.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

const val STRING_LENGTH = 15
const val RESULTS_MAX_COUNTER_VALUE = 100

class SearchViewPagerAdapter(
    fragment: Fragment,
    private val tabsCount: Int,
    private val names: List<String>?
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabsCount

    override fun createFragment(position: Int): Fragment {
        val fragment = if (names != null && names.isEmpty()) {
            SearchEventEmptyFragment()
        } else {
            SearchPagerFragment()
        }
        fragment.arguments = Bundle().apply {
            if (!names.isNullOrEmpty()) {
                putStringArrayList(ARG_OBJECT, names as ArrayList<String>)
            }
            putInt(RESULTS_COUNT, (0..RESULTS_MAX_COUNTER_VALUE).random())
            putString(KEYWORD_1, getRandomString(STRING_LENGTH))
            putString(KEYWORD_2, getRandomString(STRING_LENGTH))
        }
        return fragment
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
