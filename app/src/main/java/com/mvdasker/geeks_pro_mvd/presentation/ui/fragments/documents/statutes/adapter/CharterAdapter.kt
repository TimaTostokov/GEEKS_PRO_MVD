package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.statutes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.Charter
import com.mvdasker.geeks_pro_mvd.databinding.ItemCharterBinding

class CharterAdapter : RecyclerView.Adapter<CharterViewHolder>() {

    private var listCharters = mutableListOf<Charter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_charter, parent, false)
        return CharterViewHolder(view)
    }

    override fun getItemCount(): Int = listCharters.size

    override fun onBindViewHolder(holder: CharterViewHolder, position: Int) {
        holder.bindCharter(listCharters[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCharters(charter: List<Charter>) {
        listCharters.addAll(charter)
        notifyDataSetChanged()
    }
}

class CharterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemCharterBinding.bind(itemView)

    fun bindCharter(charter: Charter) = with(binding) {
        binding.itemDateOfCharter.text = charter.title
    }
}