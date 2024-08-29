package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartersFragment : Fragment(R.layout.fragment_charters) {

    private val binding by viewBinding(FragmentChartersBinding::bind)

    private val viewModel by viewModels<ChartersViewModel>()

    private val adapter by lazy { CharterAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

            }
        }
    }

    private fun setupRecyclerView() {
        binding.fcListCharters.layoutManager = LinearLayoutManager(requireContext())
        binding.fcListCharters.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.fcBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected -> noInternetSnackbar()
                else -> Extensions.showToast(requireContext(), "Network is disconnected")
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

                is UiState.Error -> binding.fchartProgressBar.gone()
            }
        }
    }

}