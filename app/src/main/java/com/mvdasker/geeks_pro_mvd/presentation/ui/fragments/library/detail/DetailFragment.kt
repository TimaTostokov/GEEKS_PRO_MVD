package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        goBack()
        goNotification()
        fubBtn()
        showSnack()
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.ShowProgressBar ->
                    binding.detailsProgressBar.visible()

                is Messages.HideProgressBar ->
                    binding.detailsProgressBar.gone()
            }
            viewModel.clearMessage()
        }
    }

    private fun fubBtn() {
        binding.upBtn.setOnClickListener {
            binding.nestedLibrary.smoothScrollTo(0, 0)
        }
    }

    private fun goBack() {
        binding.ivBellSecond.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun goNotification() {
        binding.flibNotification.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_notificationsFragment)
        }
    }

    private fun observe() {
        observeData(viewModel.noteDetail) { note ->
            Glide
                .with(requireContext())
                .load(note?.image)
                .into(binding.ivPhoto)
            binding.tvTextAsker.text =
                Html.fromHtml(note?.conspect ?: "", Html.FROM_HTML_MODE_LEGACY)
            binding.tvKonstpekt.text = note?.title
        }
    }

}