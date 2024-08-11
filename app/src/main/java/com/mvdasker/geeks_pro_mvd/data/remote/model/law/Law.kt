package com.mvdasker.geeks_pro_mvd.data.remote.model.law

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Law(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("text")
    val text: String? = null
): Parcelable