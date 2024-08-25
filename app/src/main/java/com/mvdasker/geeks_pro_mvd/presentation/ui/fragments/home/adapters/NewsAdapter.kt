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

class NewsAdapter(val onClick: (id: Int) -> Unit) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffUtilCallback()) {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    it.id?.let { it1 -> onClick(it1) }
                }
            }
        }

        fun bind(data: News) {
            binding.tvDate.text = data.date?.let { formatDate(it) }
            binding.tvUrgentNews.text = data.title
            binding.tvDescription.text = data.description

            if (data.images?.isNotEmpty() == true) {
                data.images.firstOrNull().let { images ->
                    Glide.with(itemView.context).load(images?.image).into(binding.ivItem)
                }
            } else {
                Glide.with(itemView.context).load(itemView.isVisible).into(binding.ivItem)
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