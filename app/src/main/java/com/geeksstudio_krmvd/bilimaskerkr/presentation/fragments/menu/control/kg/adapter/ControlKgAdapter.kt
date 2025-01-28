package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.control.kg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.mangements.Governance
import com.geeksstudio_krmvd.bilimaskerkr.databinding.ItemManagementKgBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.highlightItem
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.loadImage

class ControlKgAdapter :
    ListAdapter<Governance, ControlKgAdapter.ManagementsKgViewHolder>(DiffUtilCallback()) {

    inner class ManagementsKgViewHolder(private val binding: ItemManagementKgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Governance) {
            with(binding) {
                itemName.text = item.name
                tvData.text = item.jobTittle
                binding.imView.loadImage(item.photo.toString())
            }
        }

        fun highlightItemControl() {
            binding.llControlKr.highlightItem()
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
                newItem: Governance,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Governance,
                newItem: Governance,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    fun getPositionForId(id: Int): Int = currentList.indexOfFirst { it.id == id }

}