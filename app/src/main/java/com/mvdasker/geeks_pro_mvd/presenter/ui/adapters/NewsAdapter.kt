package com.mvdasker.geeks_pro_mvd.presenter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.DataItem
import com.mvdasker.geeks_pro_mvd.databinding.ItemNewsBinding

class NewsAdapter(private val onClick: (DataItem) -> Unit) :
    ListAdapter<DataItem, NewsAdapter.NewsViewHolder>(DiffUtilCallback()) {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: DataItem) {
            binding.ivItem.setImageResource(item.image[0])
            binding.tvData.text = item.data
            binding.tvUrgentNews.text = item.urgentNews
            binding.tvDescription.text = item.tittle
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
        class DiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}