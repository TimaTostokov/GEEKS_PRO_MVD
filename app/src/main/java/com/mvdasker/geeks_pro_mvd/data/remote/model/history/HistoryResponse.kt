package com.mvdasker.geeks_pro_mvd.data.remote.model.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("text_ky")
    val text_ky: String? = null,
    @SerializedName("text_ru")
    val text_ru: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_ky")
    val title_ky: String? = null,
    @SerializedName("title_ru")
    val title_ru: String? = null
) : Parcelable