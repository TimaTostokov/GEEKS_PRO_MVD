package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.databinding.ItemAbstractBinding

class NotesAdapterLibrary(private val onClick: (Int) -> Unit) :
    ListAdapter<Library, NotesAdapterLibrary.ViewHolder>(DiffUtilCallback()) {

    private var searchQuery: String = ""

    inner class ViewHolder(
        private val binding: ItemAbstractBinding,
        val onClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    it.id?.let { it1 -> onClick(it1) }
                }
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        fun bind(note: Library) = with(binding) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescription.text = highlightText(
                    Html.fromHtml(note.conspect, Html.FROM_HTML_MODE_LEGACY),
                    searchQuery
                )
                tvTitle.text = highlightText(
                    Html.fromHtml(note.title, Html.FROM_HTML_MODE_LEGACY),
                    searchQuery
                )
            }
        }

        @SuppressLint("ResourceAsColor")
        private fun highlightText(text: Spanned, query: String): SpannableString {
            val spannableString = SpannableString(text)
            if (query.isNotEmpty()) {
                val color = ContextCompat.getColor(binding.root.context, R.color.search_color)
                var startIndex = text.toString().lowercase().indexOf(query.lowercase())
                while (startIndex >= 0) {
                    val endIndex = startIndex + query.length
                    spannableString.setSpan(
                        ForegroundColorSpan(color),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    startIndex = text.toString().lowercase().indexOf(query.lowercase(), endIndex)
                }
            }
            return spannableString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAbstractBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchQuery(query: String) {
        searchQuery = query
        Log.d("NotesAdapter", "Search query updated: $query")
        notifyDataSetChanged()
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Library>() {
        override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean {
            return oldItem.id == newItem.id
        }
    }

}