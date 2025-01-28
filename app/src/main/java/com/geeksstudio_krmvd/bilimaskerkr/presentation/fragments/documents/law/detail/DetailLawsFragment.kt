package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.law.detail

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentDetailLawsBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailLawsFragment : Fragment(R.layout.fragment_detail_laws) {

    private val binding by viewBinding(FragmentDetailLawsBinding::bind)

    private val viewModel: DetailLawViewModel by viewModels()

    private val args: DetailLawsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.id.let {
            viewModel.setId(it)
        }

        observe()
        snackBar()
        goBack()

        binding.upBtn.setOnClickListener {
            binding.nestedScroll.smoothScrollTo(0, 0)
        }
    }

    private fun observe() {
        observeData(viewModel.lawsDetail) { data ->
            when (data) {
                is UiState.Error -> {
                    binding.detailsProgressBar.gone()
                    Log.e("error", "данные не пришли ${data.throwable}")
                }

                UiState.Loading -> binding.detailsProgressBar.visible()

                is UiState.Success -> {
                    binding.tvArticle.text =
                        Html.fromHtml(data.data?.article, Html.FROM_HTML_MODE_LEGACY)
                    binding.detailsProgressBar.gone()
                }
            }
        }
    }

    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.detailsProgressBar.visible()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}