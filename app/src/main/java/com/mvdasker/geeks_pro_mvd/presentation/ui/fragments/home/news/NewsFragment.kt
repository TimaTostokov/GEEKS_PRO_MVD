package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.news

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsImage
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding
import com.mvdasker.geeks_pro_mvd.common.ImageSlider
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.formatDate
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel by viewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setupClickListeners()
        showSnack()
    }

    private fun setupClickListeners() {
        binding.apply {
            upBtn.setOnClickListener {
                nestredScroll.smoothScrollTo(0, 0)
            }

            toBack.setOnClickListener {
                findNavController().navigateUp()
            }

            ivIcon.setOnClickListener {
                findNavController().navigate(R.id.action_newsFragment_to_notificationsFragment)
            }
        }
    }
//
//    private fun setupViewPager(imageList: List<NewsImage>) {
//        val adapter = ImageSlider(imageList)
//    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.detailState.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> Log.e("toli", "Нет новостей")

                    UiState.Loading -> binding.fNewsProgressBar.visible()

                    is UiState.Success -> {
                        with(binding) {
                            val imageUrl = uiState.data.image?.firstOrNull()?.image
                            //Glide.with(ivItem).load(imageUrl).into(ivItem)
//                            uiState.data.image?.let { setupViewPager(it) }

                            tvNewsTitle.text = uiState.data.title
                            tvData.text = Html.fromHtml(uiState.data.description, Html.FROM_HTML_MODE_LEGACY)
                            date.text = formatDate(uiState.data.date.toString())
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
                is Messages.HideProgressBar -> binding.fNewsProgressBar.gone()
                is Messages.ShowProgressBar -> {}
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.cardView.isVisible = false
                }
            }
            viewModel.clearMessage()
        }
    }

}