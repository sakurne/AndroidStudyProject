package com.example.myapplication.presentation.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchPagerBinding

const val ARG_OBJECT = "results"
const val RESULTS_COUNT = "resultsCount"
const val KEYWORD_1 = "keyword1"
const val KEYWORD_2 = "keyword2"

class SearchPagerFragment : Fragment() {

    private lateinit var binding: FragmentSearchPagerBinding
    private var names: ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            names = getStringArrayList(ARG_OBJECT) ?: arrayListOf()
            binding.pagerTextView.text =
                resources.getString(
                    R.string.search_result_text,
                    getString(KEYWORD_1),
                    getString(KEYWORD_2), getInt(RESULTS_COUNT)
                )
        }
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = names?.let { SearchPagerRecyclerAdapter(it) }
    }
}
