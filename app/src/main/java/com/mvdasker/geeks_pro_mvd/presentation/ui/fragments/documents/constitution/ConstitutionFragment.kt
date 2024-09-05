package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.Constitutions
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.adapter.ConstitutionsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionFragment : Fragment(R.layout.fragment_constitution) {

    private val binding by viewBinding(FragmentConstitutionBinding::bind)
    private val viewModel by viewModels<ConstitutionsViewModel>()
    private var mList = ArrayList<Constitutions>()
    private val adapter = ConstitutionsAdapter(mList, ::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupUI()
        observeViewModel()
        toGoSearch()
    }

    private fun initialize() {
        binding.rvConstitution.adapter = adapter
        binding.rvConstitution.setHasFixedSize(true)
    }

    private fun setupUI() {
        binding.fconstBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        observeData(viewModel.constitution) { uiState ->
            Log.d("ConstitutionFragment", "UI State: $uiState")
            when (uiState) {
                is UiState.Loading -> binding.fcLawProgressBar.visible()
                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    Log.d("LawFragment", "Полученные данные: ${uiState.data}")
                    adapter.setFilteredList(uiState.data)
                }

                is UiState.Error -> {
                    binding.fcLawProgressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных: ${uiState.message}")
                }
            }
        }

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected -> noInternetSnackbar()
                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun toGoSearch() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(ConstitutionFragmentDirections.actionConstitutionFragmentToConstitutionsSearchFragment())
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(
            ConstitutionFragmentDirections.actionConstitutionFragmentToConstitutionsDetailFragment(
                id
            )
        )
    }
}