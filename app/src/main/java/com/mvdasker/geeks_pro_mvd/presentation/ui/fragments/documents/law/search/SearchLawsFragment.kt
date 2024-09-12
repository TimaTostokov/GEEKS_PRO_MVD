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
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchLawsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.LawViewModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter.LawAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLawsFragment : Fragment(R.layout.fragment_search_laws) {

    private val binding by viewBinding(FragmentSearchLawsBinding::bind)

    private val viewModel by viewModels<LawViewModel>()

    private val adapter = LawAdapter(::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        observeViewModel()
        snackBar()
        searchInfo()
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
                    adapter.setFilteredList(
                        mList = uiState.data,
                        query = binding.etSearch.text?.toString().orEmpty(),
                    )
                }

                is UiState.Error -> {
                    binding.fcLawProgressBar.gone()
                }
            }
        }
    }

    private fun searchInfo() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun onClick(id: Int) {
        findNavController().navigate(
            SearchLawsFragmentDirections.actionSearchLawsFragmentToDetailLawsFragment(
                id
            )
        )
    }

    private fun goBack() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnCross.setOnClickListener {
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

    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.fcLawProgressBar.visible()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

}