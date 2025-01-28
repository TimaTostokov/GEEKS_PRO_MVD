package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("news")
    val history: Int? = null
) : Parcelable