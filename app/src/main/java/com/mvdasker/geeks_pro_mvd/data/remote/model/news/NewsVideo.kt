package com.mvdasker.geeks_pro_mvd.data.remote.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsVideo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("embed_code")
    val video: String? = null
) : Parcelable
