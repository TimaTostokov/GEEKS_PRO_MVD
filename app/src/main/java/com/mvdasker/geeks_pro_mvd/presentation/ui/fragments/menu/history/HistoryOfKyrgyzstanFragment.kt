package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryOfKyrgyzstanBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel.HistoryViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.loadImage
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryOfKyrgyzstanFragment : Fragment(R.layout.fragment_history_of_kyrgyzstan) {

    private val binding by viewBinding(FragmentHistoryOfKyrgyzstanBinding::bind)

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observe()
        snackBar()

        viewModel.fetchHistory(SLUG)

        binding.upBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        lifecycleScope.launch {
            viewModel.history
                .collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.fAboutKyrgyzProgressBar.visible()
                        }

                        is UiState.Error -> {
                            Log.d("ololo", "Данные не пришли: ${uiState.throwable}")
                            binding.fAboutKyrgyzProgressBar.gone()
                        }

                        is UiState.Success -> {
                            binding.fAboutKyrgyzProgressBar.gone()
                            val firstItem = uiState.data?.text
                            Log.d("ololo", "Данные: ${uiState.data}")
                            if (firstItem != null) {
                                binding.tvInfo.text =
                                    Html.fromHtml(firstItem, Html.FROM_HTML_MODE_LEGACY)
                            } else binding.tvInfo.text = getString(R.string.no_data)

                            uiState.data?.images?.get(0)?.image?.let {
                                binding.imageView.loadImage(
                                    it
                                )
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
                    Extensions.showToast(requireContext(), "Network is disconnected")
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

    companion object {
        const val SLUG = "kr"
    }

}