package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.NotePro
import com.mvdasker.geeks_pro_mvd.databinding.ItemAbstractBinding

class NotesAdapterLibrary(val onClick: (NotePro) -> Unit) :
    ListAdapter<NotePro, NotesAdapterLibrary.ViewHolder>(DiffUtilCallback()) {

    private var searchQuery: String = ""

    inner class ViewHolder(private val binding: ItemAbstractBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    onClick(it)
                }
            }
        }

        fun onBind(note: NotePro) = with(binding) {
            tvTitle.text = highlightText(note.title, searchQuery)
            tvDescription.text = highlightText(note.description, searchQuery)
        }

        private fun highlightText(text: String, query: String): SpannableString {
            val spannableString = SpannableString(text)
            val startIndex = text.lowercase().indexOf(query.lowercase())
            if (startIndex >= 0) {
                val endIndex = startIndex + query.length
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#03A9F4")),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannableString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAbstractBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<NotePro>() {
        override fun areItemsTheSame(oldItem: NotePro, newItem: NotePro): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotePro, newItem: NotePro): Boolean {
            return oldItem == newItem
        }
    }
    
}