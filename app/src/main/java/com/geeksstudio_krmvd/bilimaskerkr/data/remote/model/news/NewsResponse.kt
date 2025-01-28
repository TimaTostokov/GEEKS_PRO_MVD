package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<News>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) : Parcelable