package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.News
import com.mvdasker.geeks_pro_mvd.databinding.ItemNewsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.formatDate

class NewsAdapter(val onClick: (id: Int) -> Unit) :
    PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(DiffUtilCallback()) {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    it.id?.let { id -> onClick(id) }
                }
            }
        }

        fun bind(data: News) {
            with(binding) {
                tvDate.text = data.date?.let { formatDate(it) }
                tvUrgentNews.text = data.title
                tvDescription.text = Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY)

                val imageUrl = data.images?.firstOrNull()?.image
                Glide.with(itemView.context).load(imageUrl).into(ivItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        class DiffUtilCallback : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.date == newItem.date &&
                        oldItem.title == newItem.title &&
                        oldItem.description == newItem.description
            }
        }
    }

}