package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.databinding.ItemLawsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.highlightText
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.rotate

class LawAdapter(
    private val onChapterCLick: (Int) -> Unit,
) : RecyclerView.Adapter<LawViewHolder>() {

    private var searchQuery: String = ""
    private var mList = mutableListOf<Law>()

    fun setFilteredList(mList: List<Law>, query: String) {
        this.searchQuery = query
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LawViewHolder {
        val binding = ItemLawsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LawViewHolder(
            binding = binding,
            onClick = ::onClick,
            onChapterClick = onChapterCLick,
        )
    }

    private fun onClick(adapterPosition: Int) {
        val law = mList[adapterPosition]
        mList[adapterPosition] = law.copy(isExpandable = !law.isExpandable)
        notifyItemChanged(adapterPosition)
    }

    override fun onBindViewHolder(holder: LawViewHolder, position: Int) {
        holder.bind(mList[position], searchQuery)
    }

    override fun getItemCount(): Int = mList.size
}

class LawViewHolder(
    private val binding: ItemLawsBinding,
    onClick: (Int) -> Unit,
    onChapterClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = LawsChapterAdapter(onChapterClick)
    private var prevIsExpanded = false

    init {
        binding.ivSpinner.setOnClickListener {
            onClick.invoke(bindingAdapterPosition)
        }
        binding.rvChapter.adapter = adapter
        binding.rvChapter.layoutManager = LinearLayoutManager(itemView.context)
    }

    fun bind(lawsData: Law, searchQuery: String) {
        if (prevIsExpanded != lawsData.isExpandable) {
            binding.ivSpinner.rotate(lawsData.isExpandable)
        }
        prevIsExpanded = lawsData.isExpandable
        binding.tvLaws.highlightText(
            Html.fromHtml(lawsData.section, Html.FROM_HTML_MODE_LEGACY).toString(),
            searchQuery
        )
        lawsData.charter?.let(adapter::setChapters)

        binding.rvChapter.isVisible = lawsData.isExpandable
    }

}
