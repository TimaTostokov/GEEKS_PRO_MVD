package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.statutes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.Statute
import com.mvdasker.geeks_pro_mvd.databinding.ItemStatuteBinding

class StatuteAdapter : RecyclerView.Adapter<StatuteViewHolder>() {

    private var listStatutes = listOf(
        Statute("1", "07.08.1998"),
        Statute("2", "13.07.2004"),
        Statute("3", "30.07.2013"),
        Statute("4", "02.08.2018"),
        Statute("5", "15.01.2021"),
        Statute("6", "07.08.2023"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatuteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_statute, parent, false)
        return StatuteViewHolder(view)
    }

    override fun getItemCount(): Int = listStatutes.size

    override fun onBindViewHolder(holder: StatuteViewHolder, position: Int) {
        holder.bindCharter(listStatutes[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addStatutes(statutes: List<Statute>) {
        notifyDataSetChanged()
    }
}

class StatuteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemStatuteBinding.bind(itemView)

    fun bindCharter(statute: Statute) = with(binding) {
        binding.itemNumberOfStatute.text = statute.date
    }

}