package com.mvdasker.geeks_pro_mvd.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("images")
    val images: List<NewsImage>,
    @SerializedName("videos")
    val video: List<NewsVideo>,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_ru")
    val titleRu: String?,
    @SerializedName("title_ky")
    val titleKy: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("description_ru")
    val descriptionRu: String?,
    @SerializedName("description_ky")
    val descriptionKy: String?,
    @SerializedName("date")
    val date: String?
) : Parcelable

@Parcelize
data class NewsImage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("news")
    val news: Int
) : Parcelable

@Parcelize
data class NewsVideo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("videos")
    val video: String?,
) : Parcelable
