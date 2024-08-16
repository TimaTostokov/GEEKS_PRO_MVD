package com.mvdasker.geeks_pro_mvd.data.remote.model.library

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Library(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_ru")
    val titleRu: String,
    @SerializedName("title_ky")
    val titleKy: String,
    @SerializedName("conspect")
    val conspect: String,
    @SerializedName("conspect_ru")
    val conspectRu: String,
    @SerializedName("conspect_ky")
    val conspectKy: String,
    @SerializedName("image")
    @DrawableRes
    val image: Int? = null
) : Parcelable