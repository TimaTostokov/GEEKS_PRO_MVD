package com.mvdasker.geeks_pro_mvd.data.remote.model.history


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("images")
    val images: List<HistoryImage>? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("videos")
    val videos: List<HistoryVideo>? = null
): Parcelable