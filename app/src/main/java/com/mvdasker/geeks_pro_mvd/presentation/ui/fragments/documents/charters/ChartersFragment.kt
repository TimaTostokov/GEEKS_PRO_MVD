package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentChartersBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters.adapter.CharterAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartersFragment : Fragment(R.layout.fragment_charters) {

    private val binding by viewBinding(FragmentChartersBinding::bind)

    private val viewModel by viewModels<ChartersViewModel>()

    private val adapter = CharterAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fcListCharters.adapter = adapter
        binding.fcListCharters.layoutManager = LinearLayoutManager(requireContext())

        binding.fcBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                }
                else -> {
                    Extensions.showToast(requireContext(),"Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }

        observeData(viewModel.charters) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.fchartProgressBar.visible()

                is UiState.Success -> {
                    adapter.addCharters(uiState.data)
                    binding.fchartProgressBar.gone()
                }

                is UiState.Error ->
                    Extensions.showToast(requireContext(),"Failed 404")
            }
        }
    }

}