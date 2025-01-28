package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("news")
    val news: Int? = null
) : Parcelable