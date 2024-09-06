package com.mvdasker.geeks_pro_mvd.data.remote.model.library

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryImage
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryVideo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Library(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_ru")
    val titleRu: String? = null,
    @SerializedName("title_ky")
    val titleKy: String? = null,
    @SerializedName("conspect")
    val conspect: String? = null,
    @SerializedName("conspect_ru")
    val conspectRu: String? = null,
    @SerializedName("conspect_ky")
    val conspectKy: String? = null,
    @SerializedName("image")
    val images: List<LibraryImage>? = null,
    @SerializedName("videos")
    val videos: List<HistoryVideo>? = null
) : Parcelable