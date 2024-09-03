package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.Constitutions

class ConstitutionsAdapter(
    private var mList: List<Constitutions>,
    private val onCLick: (Int) -> Unit
) :
    RecyclerView.Adapter<ConstitutionsAdapter.ConstitutionViewHolder>() {

    private var searchQuery: String = ""

    inner class ConstitutionViewHolder(val binding: Ite) :
        RecyclerView.ViewHolder(binding.root) {
        fun collapseExpandedView() {
            binding.linear.visibility = View.GONE
        }
    }

    fun setFilteredList(mList: List<Constitutions>) {
        this.mList = mList
        notifyDataSetChanged()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstitutionViewHolder {
        val binding = ItemLawsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConstitutionViewHolder(binding)
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ConstitutionViewHolder, position: Int) {
        val ConstitutionsData = mList[position]
        holder.binding.tvLaws.text = ConstitutionsData.section
        if (ConstitutionsData.charter != null && ConstitutionsData.charter.size > 1) {
            holder.binding.tvCharter.text = ConstitutionsData.charter[0].charter
        } else {
            holder.binding.tvCharter.text = "No data available"
        }
        val isExpandable: Boolean = ConstitutionsData.isExpandable
        holder.binding.tvCharter.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.binding.ivSpinner.setOnClickListener {
            isAnyItemExpanded(position)
            ConstitutionsData.isExpandable = !ConstitutionsData.isExpandable
            notifyItemChanged(position)
        }
        holder.binding.linear.setOnClickListener {
            ConstitutionsData.id?.let { it1 -> onCLick(it1) }
        }
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = mList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position) {
            mList[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }

    override fun onBindViewHolder(
        holder: ConstitutionViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedView()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = mList.size
}