package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history

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
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryVVMVDKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel.HistoryViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.loadImage
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryVVMVDKRFragment : Fragment() {

    private var _binding: FragmentHistoryVVMVDKRBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryVVMVDKRBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

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

    private fun observe() {
        lifecycleScope.launch {
            viewModel.history
                .collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.fAboutVVMVDProgressBar.visible()
                        }

                        is UiState.Error -> {
                            Log.d("tag", "Данные не пришли: ${uiState.message}")
                            binding.fAboutVVMVDProgressBar.gone()
                        }

                        is UiState.Success -> {
                            binding.fAboutVVMVDProgressBar.gone()
                            val firstItem = uiState.data?.text
                            Log.d("ololo", "Данные не пришли: ${uiState.data}")
                            if (firstItem != null) {
                                binding.tvInfo.text =
                                    Html.fromHtml(firstItem, Html.FROM_HTML_MODE_LEGACY)
                            } else binding.tvInfo.text = getString(R.string.no_data)

                            uiState.data?.images?.get(0)?.image?.let { binding.imageView.loadImage(it) }
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
                    binding.fAboutVVMVDProgressBar.visible()
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

    companion object {
        const val SLUG = "vvmvdkr"
    }

}