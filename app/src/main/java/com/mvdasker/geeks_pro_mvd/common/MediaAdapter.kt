package com.mvdasker.geeks_pro_mvd.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.databinding.ItemImageBinding
import com.mvdasker.geeks_pro_mvd.databinding.ItemVideoBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.loadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

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

        private var youTubePlayer: YouTubePlayer? = null

        fun bind(mediaItem: PlayerItem.Video) {
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@VideoViewHolder.youTubePlayer = youTubePlayer

                    val videoId = extractYouTubeVideoId(mediaItem.videoUrl)
                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    } else {
                        Log.e("VideoViewHolder", "Invalid YouTube URL: ${mediaItem.videoUrl}")
                    }
                }
            })
        }

        private fun extractYouTubeVideoId(url: String): String? {
            val regex = ".*(?:youtu.be/|v/|embed/|watch\\?v=|v=)([^#]*).*".toRegex()
            val match = regex.matchEntire(url)
            return match?.groups?.get(1)?.value
        }

        fun releasePlayer() {
            youTubePlayer?.pause()
        }
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.imageView.loadImage(url)
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