package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionsDetailBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionsDetailFragment : Fragment(R.layout.fragment_constitutions_detail) {
    private val binding by viewBinding(FragmentConstitutionsDetailBinding::bind)
    private val viewModel: ConstitutionsDetailViewModel by viewModels()
    private val args: ConstitutionsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getConstitutionsDetailDetail(args.model)
        observe()
        goBack()
    }

    private fun observe() {
        observeData(viewModel.constitutionsDetail) { data ->
            when (data) {
                is UiState.Error -> {
                    Log.e("error", data.message )
                }

                UiState.Loading -> {
                    Log.e("error", "loading" )
                }

                is UiState.Success -> {
                    binding.tvConstSearch.text = Html.fromHtml(data.data?.article, Html.FROM_HTML_MODE_LEGACY)
                    Log.e("error", "succses" )
                }
            }
        }
    }

    private fun goBack() {
        binding.arrowSearch.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}