package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailLawsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
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
        goBack()
    }

    private fun observe() {
        observeData(viewModel.lawsDetail) { data ->
            when (data) {
                is UiState.Error -> {
                    Log.e("error", "данные не пришли")
                }

                UiState.Loading -> {
                    Log.e("error", "loading")
                }

                is UiState.Success -> {
                    binding.tvArticle.text = Html.fromHtml(  data.data?.article, Html.FROM_HTML_MODE_LEGACY)
                    Log.e("error", "succses")
                }
            }
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}