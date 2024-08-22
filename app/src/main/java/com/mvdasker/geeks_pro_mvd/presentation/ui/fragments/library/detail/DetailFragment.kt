package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvTextAsker.text =
                    Html.fromHtml(note?.conspect ?: "", Html.FROM_HTML_MODE_LEGACY)
                binding.tvKonstpekt.text = note?.title
            }
        }
    }
}