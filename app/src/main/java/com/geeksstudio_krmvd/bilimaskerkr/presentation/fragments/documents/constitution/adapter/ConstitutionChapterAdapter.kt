package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.constitution.ConstitutionsChapter
import com.geeksstudio_krmvd.bilimaskerkr.databinding.ItemChapterLawsBinding

class ConstitutionChapterAdapter(
    private val onChapterClick: (Int) -> Unit,
) : RecyclerView.Adapter<ConstitutionChapterViewHolder>() {
    private val constitutionChapterList = mutableListOf<ConstitutionsChapter>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConstitutionChapterViewHolder {
        val binding =
            ItemChapterLawsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConstitutionChapterViewHolder(binding, onChapterClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChapters(chapter: List<ConstitutionsChapter>) {
        constitutionChapterList.clear()
        constitutionChapterList.addAll(chapter)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ConstitutionChapterViewHolder, position: Int) {
        holder.bind(constitutionChapterList[position])
    }

    override fun getItemCount(): Int = constitutionChapterList.size
}

class ConstitutionChapterViewHolder(
    private val binding: ItemChapterLawsBinding,
    private val onCLick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var chapter: ConstitutionsChapter? = null

    init {
        binding.linear.setOnClickListener {
            chapter?.id?.let(onCLick)
        }
    }

    fun bind(data: ConstitutionsChapter) {
        this.chapter = data
        binding.tvCharter.text = data.chapter.toString()
    }

}