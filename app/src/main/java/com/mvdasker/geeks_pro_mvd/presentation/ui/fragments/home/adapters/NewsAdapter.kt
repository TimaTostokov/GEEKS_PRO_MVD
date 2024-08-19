package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.News
import com.mvdasker.geeks_pro_mvd.databinding.ItemNewsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.formatDate

class NewsAdapter(val onClick: (id: String) -> Unit) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffUtilCallback()) {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    onClick(it.id.toString())
                }
            }
        }

        fun bind(data: News) {
            binding.tvDate.text = data.date?.let { formatDate(it) }
            binding.tvUrgentNews.text = data.title
            binding.tvDescription.text = data.description

            if (data.images?.isNotEmpty() == true) {
                data.images.firstOrNull()?.let { image ->
                    Glide.with(itemView.context).load(image.image).into(binding.ivItem)
                }
            } else {
                Glide.with(itemView.context).load(R.drawable.about_as).into(binding.ivItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        class DiffUtilCallback : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}