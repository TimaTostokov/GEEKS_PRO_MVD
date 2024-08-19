package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLawBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.disableScreenShot
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LawFragment : Fragment(R.layout.fragment_law) {

    private val binding by viewBinding(FragmentLawBinding::bind)

    private val viewModel by viewModels<LawViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        disableScreenShot(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSnack()
        observe()

        binding.flBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {
        observeData(viewModel.law) {
            when (it) {
                UiState.Loading -> {
                    binding.fcLawProgressBar.visible()
                }

                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    Log.d("toli", "данные пришли")
                }

                is UiState.Error -> {
                    Log.e("toli", "данные не пришли frag")
                    binding.fcLawProgressBar.gone()
                }
            }
        }
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                else -> {}
            }
            viewModel.clearMessage()
        }
    }

    override fun onDetach() {
        super.onDetach()
        disableScreenShot(false)
    }

}