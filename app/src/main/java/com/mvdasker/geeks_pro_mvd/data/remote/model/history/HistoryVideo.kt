package com.mvdasker.geeks_pro_mvd.data.remote.model.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryVideo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("embed_code")
    val video: String? = null
) : Parcelable
