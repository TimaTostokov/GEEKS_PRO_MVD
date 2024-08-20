package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.formatDate
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NewsFragmentArgs>()

    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        toBack()
        toNotification()
        showSnack()

        args.id?.let {
            viewModel.setId(it)
        }

        binding.upBtn.setOnClickListener {
            binding.nestredScroll.smoothScrollTo(0, 0)
        }
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.detailState.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> Log.e("toli", "нету новостей")

                    UiState.Loading -> {
                        binding.fNewsProgressBar.visible()
                    }

                    is UiState.Success -> {
                        uiState.data.let {
                            val imageUrl = if (!it.image.isNullOrEmpty()) {
                                it.image[0].image
                            } else null
                            Glide.with(binding.ivItem).load(imageUrl).into(binding.ivItem)

                            binding.tvNewsTitle.text = it.title
                            binding.tvData.text = it.description
                            binding.date.text = formatDate(it.date.toString())
                        }
                        binding.fNewsProgressBar.gone()
                    }
                }
            }
        }
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) {
            when (it) {
                is Messages.HideProgressBar ->
                    binding.fNewsProgressBar.gone()

                is Messages.ShowProgressBar -> {}

                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.cardView.isVisible = false
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun toBack() {
        binding.toBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun toNotification() {
        binding.ivIcon.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_notificationsFragment)
        }
    }

}