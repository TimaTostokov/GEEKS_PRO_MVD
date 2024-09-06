package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.MediaAdapter
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.PlayerItem
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsImage
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.formatDate
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.mapToMediaItems
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

    @SuppressLint("SetTextI18n")
    private fun getLists(image: List<NewsImage>?, video: List<String>) {
        val mediaItems = image?.let { image ->
            mapToMediaItems(
                video,
                image,
                { mediaPlayer -> mediaPlayer.let { PlayerItem.Video(it) } },
                { imageSlider -> imageSlider.image?.let { PlayerItem.Image(it) } }
            )
        }
        val itemCount = mediaItems?.size ?: 0
        val adapter = mediaItems?.let { MediaAdapter(it) }

        binding.apply {
            viewPager.adapter = adapter

            pageIndicator.visibility = if (itemCount > 1) View.VISIBLE else View.GONE
            prevButton.visibility =
                if (itemCount <= 1 || viewPager.currentItem == 0) View.GONE else View.VISIBLE
            nextButton.visibility =
                if (itemCount <= 1 || viewPager.currentItem == itemCount - 1) View.GONE else View.VISIBLE

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    pageIndicator.text = "${position + 1}/$itemCount"
                    prevButton.visibility = if (position == 0) View.GONE else View.VISIBLE
                    nextButton.visibility =
                        if (position == itemCount - 1) View.GONE else View.VISIBLE
                }
            })
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            upBtn.setOnClickListener {
                nestredScroll.smoothScrollTo(0, 0)
            }

            toBack.setOnClickListener {
                findNavController().navigateUp()
            }

            prevButton.setOnClickListener {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) {
                    viewPager.currentItem = currentItem - 1
                }
            }

            nextButton.setOnClickListener {
                val currentItem = viewPager.currentItem
                if (currentItem < (viewPager.adapter?.itemCount ?: 0) - 1) {
                    viewPager.currentItem = currentItem + 1
                }
            }
        }
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.detailState.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> Log.e("toli", "Нет новостей")

                    UiState.Loading -> binding.fNewsProgressBar.visible()

                    is UiState.Success -> {
                        val video = Extensions.convertToUrlArray(uiState.data.video) { newsVideo ->
                            val htmlString = newsVideo.video
                            val regex = """src="([^"]+)"""".toRegex()
                            val matchResult = regex.find(htmlString.toString())
                            matchResult?.groups?.get(1)?.value
                        }
                        getLists(uiState.data.image, video)
                        with(binding) {
                            tvNewsTitle.text = uiState.data.title
                            tvData.text =
                                Html.fromHtml(uiState.data.description, Html.FROM_HTML_MODE_LEGACY)
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