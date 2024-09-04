package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchLawsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter.LawAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLawsFragment : Fragment(R.layout.fragment_search_laws) {

    private val binding by viewBinding(FragmentSearchLawsBinding::bind)
    private var mList = ArrayList<Law>()
//    private val adapter = LawAdapter(mList)
    private var searchQuery: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

    }

    private fun initialize() {
        binding.rvLawsSearch.adapter
    }

//    fun onCLick(id: Int) {
//    findNavController().navigate(SearchLawsFragmentDirections.actionSearchLawsFragmentToDetailLawsFragment(id))
//    }
}