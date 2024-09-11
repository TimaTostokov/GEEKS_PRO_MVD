package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.MediaAdapter
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.PlayerItem
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.LibraryImage
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.mapToMediaItems
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
        setupClickListeners()
        showSnack()
    }

    @SuppressLint("SetTextI18n")
    private fun getLists(image: List<LibraryImage>?, video: List<String>) {
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
                binding.nestedLibrary.smoothScrollTo(0, 0)
            }

            ivBellSecond.setOnClickListener {
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

    private fun observe() {
        observeData(viewModel.noteDetail) { note ->
            val video = Extensions.convertToUrlArray(note?.videos) { newsVideo ->
                val htmlString = newsVideo.video
                val regex = """src="([^"]+)"""".toRegex()
                val matchResult = regex.find(htmlString.toString())
                matchResult?.groups?.get(1)?.value
            }
            getLists(note?.images, video)
            binding.tvTextAsker.text =
                Html.fromHtml(note?.conspect ?: "", Html.FROM_HTML_MODE_LEGACY)
            binding.tvKonstpekt.text = note?.title
        }
    }

}