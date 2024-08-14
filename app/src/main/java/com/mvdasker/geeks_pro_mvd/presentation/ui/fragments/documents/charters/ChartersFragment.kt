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
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.databinding.FragmentChartersBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters.adapter.CharterAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.snackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartersFragment : Fragment(R.layout.fragment_charters) {

    private val binding by viewBinding(FragmentChartersBinding::bind)

    private val viewModel: ChartersViewModel by viewModels()

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
                is Messages.NetworkIsDisconnected ->
                    snackbar("Connect to Internet...")
                else -> {
                }
            }
            viewModel.clearMessage()
        }

        observeData(viewModel.charters) { uiState ->
            when (uiState) {
                is UiState.Loading -> onLoading()
                is UiState.Success -> onSuccess(data = uiState.data)
                is UiState.Error -> onError()
            }
        }
    }

    private fun onLoading() {}

    private fun onSuccess(data: List<Charter>) {
        adapter.addCharters(data)
    }

    private fun onError() {}

}