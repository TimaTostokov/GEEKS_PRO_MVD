package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters.adapter

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.databinding.ItemCharterBinding

class CharterAdapter(private val context: Context) :
    RecyclerView.Adapter<CharterAdapter.CharterViewHolder>() {

    inner class CharterViewHolder(val binding: ItemCharterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemDownloadBtn.setOnClickListener {
                val url = it.tag as? String
                url?.let { characterUrl ->
                    downloadCharter(characterUrl)
                }
            }
        }

        fun bindCharter(charter: Charter) = with(binding) {
            itemDateOfCharter.text = charter.title
            itemDownloadBtn.tag = charter.url
        }
    }

    fun downloadCharter(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading file")
            .setDescription("Downloading from $url")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                Uri.parse(url).lastPathSegment
            )

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private var listCharters = mutableListOf<Charter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharterViewHolder {
        val binding = ItemCharterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharterViewHolder, position: Int) {
        holder.bindCharter(listCharters[position])
    }

    override fun getItemCount(): Int = listCharters.size

    @SuppressLint("NotifyDataSetChanged")
    fun addCharters(charter: List<Charter>) {
        listCharters.clear()
        listCharters.addAll(charter)
        notifyDataSetChanged()
    }

}