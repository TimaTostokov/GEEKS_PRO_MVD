package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsChapter
import com.mvdasker.geeks_pro_mvd.databinding.ItemChapterLawsBinding

class LawsChapterAdapter(
    private val onChapterClick: (Int) -> Unit,
) : RecyclerView.Adapter<LawsChapterViewHolder>() {
    private val lawsChapterList = mutableListOf<LawsChapter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawsChapterViewHolder {
        val binding =
            ItemChapterLawsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawsChapterViewHolder(binding, onChapterClick)
    }

    fun setChapters(chapter: List<LawsChapter>) {
        lawsChapterList.clear()
        lawsChapterList.addAll(chapter)
    }

    override fun getItemCount(): Int = lawsChapterList.size

    override fun onBindViewHolder(holder: LawsChapterViewHolder, position: Int) {
        holder.bind(lawsChapterList[position])
    }
}

class LawsChapterViewHolder(
    private val binding: ItemChapterLawsBinding,
    private val onCLick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var chapter: LawsChapter? = null

    init {
        binding.linear.setOnClickListener {
            chapter?.id?.let(onCLick)
        }
    }

    fun bind(data: LawsChapter) {
        this.chapter = data
        binding.tvCharter.text = data.chapter.toString()
    }

}
