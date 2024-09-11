package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
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
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryImage
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryMVDKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel.HistoryViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.loadImage
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.mapToMediaItems
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryMVDKRFragment : Fragment(R.layout.fragment_history_m_v_d_k_r) {

    private val binding by viewBinding(FragmentHistoryMVDKRBinding::bind)

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observe()
        snackBar()

        viewModel.fetchHistory(SLUG)

        binding.upBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getLists(image: List<HistoryImage>?, video: List<String>) {
        val mediaItems = image?.let { image->
            mapToMediaItems(
                video,
                image,
                { mediaPlayer -> mediaPlayer.let { PlayerItem.Video(it) } },
                { imageSlider -> imageSlider.image?.let { PlayerItem.Image(it) } }
            )
        }

        Log.d("getLists", "Not video $mediaItems")

        val itemCount = mediaItems?.size ?: 0
        val adapter = mediaItems?.let { MediaAdapter(it) }

        binding.apply {
            viewPager.adapter = adapter

            pageIndicator.visibility = if (itemCount > 1) View.VISIBLE else View.GONE
            prevButton.visibility = if (itemCount <= 1 || viewPager.currentItem == 0) View.GONE else View.VISIBLE
            nextButton.visibility = if (itemCount <= 1 || viewPager.currentItem == itemCount - 1) View.GONE else View.VISIBLE

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    pageIndicator.text = "${position + 1}/$itemCount"
                    prevButton.visibility = if (position == 0) View.GONE else View.VISIBLE
                    nextButton.visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE
                }
            })
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.history
                .collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.fAboutUsProgressBar.visible()
                        }
                        is UiState.Error -> {
                            Log.d("tag", "Данные не пришли: ${uiState.message}")
                            binding.fAboutUsProgressBar.gone()
                        }
                        is UiState.Success -> {
                            val video = Extensions.convertToUrlArray(uiState.data?.videos) { newsVideo ->
                                val htmlString = newsVideo.video
                                val regex = """src="([^"]+)"""".toRegex()
                                val matchResult = regex.find(htmlString.toString())
                                matchResult?.groups?.get(1)?.value
                            }
                            getLists(uiState.data?.images, video)
                            val firstItem = uiState.data?.text
                            if (firstItem != null) {
                                binding.tvInfo.text =
                                    Html.fromHtml(firstItem, Html.FROM_HTML_MODE_LEGACY)
                            } else binding.tvInfo.text = getString(R.string.no_data)
                            binding.fAboutUsProgressBar.gone()
                        }
                    }
                }
        }
    }


    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.fAboutUsProgressBar.visible()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun initListeners() {
        binding.apply {
            btnBack.setOnClickListener {
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

    companion object {
        const val SLUG = "history-mvdkr"
    }

}