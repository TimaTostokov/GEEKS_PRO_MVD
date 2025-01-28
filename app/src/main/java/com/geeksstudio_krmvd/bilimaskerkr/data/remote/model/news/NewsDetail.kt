package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDetail(
    @SerializedName("images")
    val image: List<NewsImage>? = null,
    @SerializedName("videos")
    val video: List<NewsVideo>? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_ru")
    val titleRu: String? = null,
    @SerializedName("title_ky")
    val titleKr: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("description_ru")
    val descriptionRu: String? = null,
    @SerializedName("description_ky")
    val descriptionKr: String? = null,
    @SerializedName("date")
    val date: String? = null
): Parcelable