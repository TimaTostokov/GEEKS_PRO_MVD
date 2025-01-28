package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.constitution.Constitutions
import com.geeksstudio_krmvd.bilimaskerkr.databinding.ItemConstitutionBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.highlightItemCard
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.highlightText
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.rotate

class ConstitutionsAdapter(
    private val onChapterCLick: (Int) -> Unit,
) : RecyclerView.Adapter<ConstitutionsViewHolder>() {

    private var searchQuery: String = ""
    private var mList = mutableListOf<Constitutions>()

    private val highlightedPositions = mutableSetOf<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: List<Constitutions>, query: String) {
        this.searchQuery = query
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstitutionsViewHolder {
        val binding =
            ItemConstitutionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConstitutionsViewHolder(
            binding = binding,
            onClick = ::onClick,
            onChapterClick = onChapterCLick,
        )
    }

    private fun onClick(adapterPosition: Int) {
        highlightedPositions.clear()
        val constitution = mList[adapterPosition]
        mList[adapterPosition] = constitution.copy(isExpandable = !constitution.isExpandable)
        notifyItemChanged(adapterPosition)
    }

    override fun onBindViewHolder(holder: ConstitutionsViewHolder, position: Int) {
        holder.bind(mList[position], searchQuery)
        if (highlightedPositions.contains(position)) {
            holder.highlightItemConstitution()
        }
    }

    override fun getItemCount(): Int = mList.size

    fun getPositionForId(id: Int): Int = mList.indexOfFirst { it.id == id }

    fun highlightItemConstitutionAtPosition(position: Int) {
        highlightedPositions.add(position)
        notifyItemChanged(position)
    }
}

class ConstitutionsViewHolder(
    private val binding: ItemConstitutionBinding,
    onClick: (Int) -> Unit,
    onChapterClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = ConstitutionChapterAdapter(onChapterClick)
    private var prevIsExpanded = false

    init {
        binding.ivSpinner.setOnClickListener {
            onClick.invoke(bindingAdapterPosition)
            binding.tvSpinner.isVisible = prevIsExpanded
        }
        binding.rvChapter.adapter = adapter
        binding.rvChapter.layoutManager = LinearLayoutManager(itemView.context)
    }

    fun bind(constitutionsData: Constitutions, searchQuery: String) {
        if (prevIsExpanded != constitutionsData.isExpandable) {
            binding.ivSpinner.rotate(constitutionsData.isExpandable)
        }
        prevIsExpanded = constitutionsData.isExpandable
        binding.tvLaws.highlightText(
            Html.fromHtml(constitutionsData.section, Html.FROM_HTML_MODE_LEGACY).toString(),
            searchQuery
        )
        constitutionsData.chapters?.let(adapter::setChapters)

        binding.rvChapter.isVisible = constitutionsData.isExpandable
    }

    fun highlightItemConstitution() {
        binding.constitutionCardView.highlightItemCard()
    }

}