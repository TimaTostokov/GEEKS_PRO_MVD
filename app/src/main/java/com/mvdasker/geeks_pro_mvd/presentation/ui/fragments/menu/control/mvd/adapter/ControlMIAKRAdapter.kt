package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.adapter

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
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.databinding.ItemManagementKgBinding

class ControlMIAKRAdapter :
    ListAdapter<Governance, ControlMIAKRAdapter.ManagmentsKgViewHolder>(DiffUtilCallback()) {
    private var searchQuery: String = ""

    inner class ManagmentsKgViewHolder(private val binding: ItemManagementKgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Governance) {
            binding.itemName.text = item.name
            binding.tvData.text = item.category?.let { highlightText(it, searchQuery) }
            Glide.with(itemView.context).load(item.photo).into(binding.imView)
        }
    }

    private fun highlightText(text: String, query: String): SpannableString {
        val spannableString = SpannableString(text)
        if (query.isNotEmpty()) {
            var startIndex = text.lowercase().indexOf(query.lowercase())
            while (startIndex >= 0) { // Подсвечиваем все вхождения запроса
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagmentsKgViewHolder {
        return ManagmentsKgViewHolder(
            ItemManagementKgBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query

    }

    override fun onBindViewHolder(holder: ManagmentsKgViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        class DiffUtilCallback : DiffUtil.ItemCallback<Governance>() {
            override fun areItemsTheSame(
                oldItem: Governance, newItem: Governance
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Governance, newItem: Governance
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}