package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionBinding
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
        binding.rvLaws.adapter = adapter
        binding.rvLaws.setHasFixedSize(true)
    }

    private fun setupUI() {
        binding.flBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        observeData(viewModel.law) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.fcLawProgressBar.visible()
                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    adapter.setFilteredList(uiState.data)
                }

                is UiState.Error -> {
                    binding.fcLawProgressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
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
            findNavController().navigate()
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate()
    }
}