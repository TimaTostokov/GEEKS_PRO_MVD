package com.mvdasker.geeks_pro_mvd.data.remote.model.news

import com.google.gson.annotations.SerializedName

data class NewsDetail(
    @SerializedName("images")
    val image: List<NewsImage>,
    @SerializedName("videos")
    val video: List<NewsVideo>,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_ru")
    val titleRu: String,
    @SerializedName("title_ky")
    val titleKr: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_ru")
    val descriptionRu: String,
    @SerializedName("description_ky")
    val descriptionKr: String,
    @SerializedName("date")
    val date: String
)