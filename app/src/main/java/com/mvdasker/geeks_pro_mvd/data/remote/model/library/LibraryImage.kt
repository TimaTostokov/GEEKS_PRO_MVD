package com.mvdasker.geeks_pro_mvd.data.remote.model.library

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("news")
    val history: Int? = null
) : Parcelable
