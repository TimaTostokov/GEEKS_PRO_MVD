package com.mvdasker.geeks_pro_mvd.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.mediaplayer.MediaPlayer
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsImage

class ImageSlider(
    private val images: List<MediaPlayer>,
    private val mediaPlayer: List<MediaPlayer>
) : RecyclerView.Adapter<ImageSlider.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_and_video, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size
}