package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.control.mvd.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.mangements.Governance
import com.geeksstudio_krmvd.bilimaskerkr.databinding.ItemManagementKgBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.highlightItem
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.loadImage

class ControlMIAKRAdapter :
    ListAdapter<Governance, ControlMIAKRAdapter.ManagmentsKgViewHolder>(DiffUtilCallback()) {

    private var searchQuery: String = ""

    inner class ManagmentsKgViewHolder(private val binding: ItemManagementKgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Governance) {
            with(binding) {
                itemName.text = item.name
                tvData.text =
                    highlightText(
                        Html.fromHtml(item.jobTittle, Html.FROM_HTML_MODE_LEGACY).toString(),
                        searchQuery
                    )
                binding.imView.loadImage(item.photo.toString())
            }
        }

        fun highlightItemControlMIAKR() {
            binding.llControlKr.highlightItem()
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagmentsKgViewHolder {
        return ManagmentsKgViewHolder(
            ItemManagementKgBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchQuery(query: String) {
        searchQuery = query
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ManagmentsKgViewHolder, position: Int) {
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

    fun getPositionForId(id: Int): Int = currentList.indexOfFirst { it.id == id }

}