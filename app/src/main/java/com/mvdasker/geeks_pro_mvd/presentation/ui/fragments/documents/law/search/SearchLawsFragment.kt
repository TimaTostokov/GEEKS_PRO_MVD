package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchLawsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.LawViewModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter.LawAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLawsFragment : Fragment(R.layout.fragment_search_laws) {

    private val binding by viewBinding(FragmentSearchLawsBinding::bind)
    private var mList = ArrayList<Law>()
    private var controlList: List<Law> = listOf()
    private val viewModel by viewModels<LawViewModel>()

    private var searchQuery: String = ""
    private val adapter = LawAdapter(mList, ::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observeViewModel()
        searchInfo()
        searchCharacter(searchQuery)
        goBack()
        deleteClearBtn()
    }

    private fun initialize() {
        binding.rvLawsSearch.adapter = adapter
    }

    private fun observeViewModel() {
        observeData(viewModel.law) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.fcLawProgressBar.visible()
                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    adapter.setFilteredList(uiState.data)
                    controlList = uiState.data
                }

                is UiState.Error -> {
                    binding.fcLawProgressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
                }
            }
        }
    }

    private fun searchInfo() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s.toString().let {
                    searchCharacter(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun searchCharacter(query: String) {
        adapter.updateSearchQuery(query)
        val filteredList = controlList.filter {
            it.section?.contains(query, ignoreCase = true) == true || it.section?.contains(
                query,
                ignoreCase = true
            ) == true
        }
        adapter.setFilteredList(filteredList)
    }

    private fun onClick(id: Int) {
        findNavController().navigate(SearchLawsFragmentDirections.actionSearchLawsFragmentToDetailLawsFragment(id))
    }


    private fun goBack() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnCross.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun deleteClearBtn() {
        binding.deleteClearBtn.setOnClickListener {
            if (binding.etSearch.text != null) {
                binding.etSearch.text = null
                binding.etSearch.clearFocus()
            }
        }
    }
}