package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryOfKyrgyzstanBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel.HistoryViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryOfKyrgyzstanFragment : Fragment() {

    private var _binding: FragmentHistoryOfKyrgyzstanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryOfKyrgyzstanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observe()
        snackBar()

        val pk = 1
        viewModel.fetchHistory(pk)

        binding.upBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.history
                .collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.fAboutKyrgyzProgressBar.visible()
                        }

                        is UiState.Error -> {
                            Log.d("tag", "Данные не пришли: ${uiState.message}")
                            binding.fAboutKyrgyzProgressBar.gone()
                        }

                        is UiState.Success -> {
                            binding.fAboutKyrgyzProgressBar.gone()
                            val firstItem = uiState.data?.text_ru
                            if (firstItem != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.tvInfo.text =
                                        Html.fromHtml(firstItem, Html.FROM_HTML_MODE_LEGACY)
                                }
                            } else {
                                binding.tvInfo.text = "Нет данных"
                            }
                        }
                    }
                }
        }
    }

    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.fAboutKyrgyzProgressBar.visible()
                }
                else -> {
                    Extensions.showToast(requireContext(), "Failed to connect progress bar")
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}