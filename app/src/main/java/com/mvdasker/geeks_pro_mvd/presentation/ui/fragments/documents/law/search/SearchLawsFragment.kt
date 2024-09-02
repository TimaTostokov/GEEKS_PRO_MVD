package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchLawsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLawsFragment : Fragment(R.layout.fragment_search_laws) {

    private val binding by viewBinding(FragmentSearchLawsBinding::bind)

    private var searchQuery: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}