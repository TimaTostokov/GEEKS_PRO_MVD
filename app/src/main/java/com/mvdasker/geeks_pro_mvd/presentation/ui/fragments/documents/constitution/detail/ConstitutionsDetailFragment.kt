package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.detail

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.ConstitutionsChapter
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionsDetailBinding
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailLawsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail.DetailLawViewModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail.DetailLawsFragmentArgs
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionsDetailFragment : Fragment(R.layout.fragment_constitutions_detail) {

    private val binding by viewBinding(FragmentConstitutionsDetailBinding::bind)

    private val viewModel: ConstitutionsDetailViewModel by viewModels()

    private val args: ConstitutionsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.id.let {
            viewModel.setId(it)
        }

        observe()
        snackBar()
        goBack()

        binding.upBtn.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }
    }

    private fun observe() {
        observeData(viewModel.constitutionsDetail) { data ->
            when (data) {
                is UiState.Error -> {
                    binding.detailsProgressBar.gone()
                    Log.e("error", "данные не пришли ${data.throwable}")
                }

                UiState.Loading -> binding.detailsProgressBar.visible()

                is UiState.Success -> {
                    binding.tvArticle.text = Html.fromHtml(  data.data?.article, Html.FROM_HTML_MODE_LEGACY)
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