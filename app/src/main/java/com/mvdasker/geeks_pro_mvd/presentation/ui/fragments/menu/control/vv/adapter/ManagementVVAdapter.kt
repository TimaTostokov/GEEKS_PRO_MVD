package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.databinding.ItemManagementKgBinding

class ManagementVVAdapter :
    ListAdapter<Governance, ManagementVVAdapter.ManagementsKgViewHolder>(DiffUtilCallback()) {

    private var searchQuery: String = ""

    inner class ManagementsKgViewHolder(private val binding: ItemManagementKgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Governance) {
            binding.itemName.text = item.name
            binding.tvData.text = item.category?.let { highlightText(it, searchQuery) }
            Glide.with(itemView.context).load(item.photo).into(binding.imView)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun highlightText(text: String, query: String): SpannableString {
        val spannableString = SpannableString(text)
        if (query.isNotEmpty()) {
            var startIndex = text.lowercase().indexOf(query.lowercase())
            while (startIndex >= 0) {
                val endIndex = startIndex + query.length
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#03A9F4")),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                startIndex = text.lowercase().indexOf(query.lowercase(), endIndex)
            }
        }
        return spannableString
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagementsKgViewHolder {
        return ManagementsKgViewHolder(
            ItemManagementKgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchQuery(query: String) {
        searchQuery = query
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ManagementsKgViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Governance>() {
        override fun areItemsTheSame(
            oldItem: Governance,
            newItem: Governance
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Governance,
            newItem: Governance
        ): Boolean {
            return oldItem == newItem
        }
    }

}