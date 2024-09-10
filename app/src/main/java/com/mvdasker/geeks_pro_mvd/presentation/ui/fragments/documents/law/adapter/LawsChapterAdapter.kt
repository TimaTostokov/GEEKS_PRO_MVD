package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsChapter
import com.mvdasker.geeks_pro_mvd.databinding.ItemChapterLawsBinding

class LawsChapterAdapter(
    private val lawsChapterList: List<LawsChapter>,
    private val onCLick: (Int) -> Unit,
) :
    RecyclerView.Adapter<LawsChapterAdapter.LawsChapterViewHolder>() {
    inner class LawsChapterViewHolder(val binding: ItemChapterLawsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LawsChapter) {
            binding.tvCharter.text = data.chapter.toString()
        }
        init {
            binding.linear.setOnClickListener {
                onCLick(it.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawsChapterViewHolder {
        val binding =
            ItemChapterLawsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawsChapterViewHolder(binding)
    }

    override fun getItemCount(): Int = lawsChapterList.size

    override fun onBindViewHolder(holder: LawsChapterViewHolder, position: Int) {
        holder.bind(lawsChapterList[position])
    }
}