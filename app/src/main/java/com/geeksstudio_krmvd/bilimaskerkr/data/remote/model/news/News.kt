package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("images")
    val images: List<NewsImage>? = null,
    @SerializedName("videos")
    val video: List<NewsVideo>? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_ru")
    val titleRu: String? = null,
    @SerializedName("title_ky")
    val titleKy: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("description_ru")
    val descriptionRu: String? = null,
    @SerializedName("description_ky")
    val descriptionKy: String? = null,
    @SerializedName("date")
    val date: String? = null
) : Parcelable