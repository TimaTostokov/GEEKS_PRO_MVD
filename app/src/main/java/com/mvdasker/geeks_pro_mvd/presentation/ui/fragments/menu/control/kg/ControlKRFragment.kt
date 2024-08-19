package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.kg

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentControlKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.kg.adapter.ControlKgAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControlKRFragment : Fragment(R.layout.fragment_control_k_r) {

    private val binding by viewBinding(FragmentControlKRBinding::bind)

    private val managementAdapter = ControlKgAdapter()

    private val viewModel by viewModels<ControlKgVIewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        goBack()
        showSnack()
    }

    private fun initialize() {
        binding.rvControll.adapter = managementAdapter
    }

    private fun setupListeners() {
        observeData(viewModel.managementState) { state ->
            when (state) {
                is UiState.Error -> {
                    Log.e("management", "данные не получены: ")
                    binding.fcRukKrProgressBar.gone()
                }

                UiState.Loading -> {
                    binding.fcRukKrProgressBar.visible()
                }

                is UiState.Success -> {
                    managementAdapter.submitList(state.data)
                    binding.fcRukKrProgressBar.gone()
                }
            }
        }
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.ShowProgressBar ->
                    binding.fcRukKrProgressBar.visible()

                is Messages.HideProgressBar ->
                    binding.fcRukKrProgressBar.gone()
            }
            viewModel.clearMessage()
        }
    }

    private fun goBack() {
        binding.vvKRBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}