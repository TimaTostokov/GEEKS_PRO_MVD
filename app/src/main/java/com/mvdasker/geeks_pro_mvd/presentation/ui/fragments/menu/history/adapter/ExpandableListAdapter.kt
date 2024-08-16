package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.parent.ParentModel
import com.mvdasker.geeks_pro_mvd.databinding.ParentItemBinding

class HistoryAdapter(private var mList: List<ParentModel>, var onClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ParentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: ParentModel) {
            binding.parentTitle.text = history.title
        }

        fun collapseExpandedView() {
            binding.parentTitle.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ParentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = mList[position]
        holder.bind(history)

        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
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