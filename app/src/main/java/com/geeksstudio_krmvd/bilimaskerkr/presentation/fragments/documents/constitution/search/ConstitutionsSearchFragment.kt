package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentConstitutionsSearchBinding
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.ConstitutionsViewModel
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.adapter.ConstitutionsAdapter
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionsSearchFragment : Fragment(R.layout.fragment_constitutions_search) {

    private val binding by viewBinding(FragmentConstitutionsSearchBinding::bind)

    private val viewModel by viewModels<ConstitutionsViewModel>()

    private val adapter = ConstitutionsAdapter(::onClick)

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
        binding.rvSearchConstitution.adapter = adapter
    }

    private fun observeViewModel() {
        observeData(viewModel.constitution) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.progressBar.visible()
                is UiState.Success -> {
                    binding.progressBar.gone()
                    adapter.setFilteredList(
                        mList = uiState.data,
                        query = binding.etSearch.text?.toString().orEmpty(),
                    )
                }

                is UiState.Error -> {
                    binding.progressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
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
            ConstitutionsSearchFragmentDirections.actionConstitutionsSearchFragmentToConstitutionsDetailFragment(
                id
            )
        )
    }

    private fun goBack() {
        binding.fconstBackBtn.setOnClickListener {
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
                    binding.progressBar.visible()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

}