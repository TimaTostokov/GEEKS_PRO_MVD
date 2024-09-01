package com.mvdasker.geeks_pro_mvd.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.databinding.ItemImageBinding
import com.mvdasker.geeks_pro_mvd.databinding.ItemVideoBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.ExoPlayerUtil

class MediaAdapter(private val items: List<PlayerItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_VIDEO = 0
        private const val TYPE_IMAGE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PlayerItem.Video -> TYPE_VIDEO
            is PlayerItem.Image -> TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_VIDEO) {
            val binding =
                ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VideoViewHolder(binding)
        } else {
            val binding =
                ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> holder.bind(items[position] as PlayerItem.Video)
            is ImageViewHolder -> holder.bind((items[position] as PlayerItem.Image).imageUrl)
        }
    }

    override fun getItemCount() = items.size

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var exoPlayer: ExoPlayer? = null

        @OptIn(UnstableApi::class)
        fun bind(mediaItem: PlayerItem.Video) {
            Log.d("VideoViewHoldr", "Initializing ExoPlayer for URL: ${mediaItem.videoUrl}")
            exoPlayer = ExoPlayerUtil.initializePlayer(binding.root.context, mediaItem.videoUrl)
            val hlsMediaSource =
                HlsMediaSource.Factory(DefaultHttpDataSource.Factory()).createMediaSource(
                    MediaItem.fromUri(mediaItem.videoUrl)
                )
            exoPlayer?.setMediaSource(hlsMediaSource)
            binding.videoView.player = exoPlayer
        }

        fun releasePlayer() {
            ExoPlayerUtil.releasePlayer(exoPlayer)
            exoPlayer = null
        }
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            Glide.with(binding.root.context).load(url).into(binding.imageView)
            ZoomHelper.addZoomableView(binding.imageView)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is VideoViewHolder) {
            holder.releasePlayer()
        }
    }

}