package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.kg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.databinding.ItemManagementKgBinding

class ControlKgAdapter :
    ListAdapter<Governance, ControlKgAdapter.ManagementsKgViewHolder>(DiffUtilCallback()) {

    inner class ManagementsKgViewHolder(private val binding: ItemManagementKgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Governance) {
            binding.itemName.text = item.name
            binding.tvData.text = item.category
            Glide.with(itemView.context).load(item.photo).into(binding.imView)
        }
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

    override fun onBindViewHolder(holder: ManagementsKgViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
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
                return oldItem.id == newItem.id
            }
        }
    }

}