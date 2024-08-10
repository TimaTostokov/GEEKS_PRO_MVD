package com.mvdasker.geeks_pro_mvd.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Charter(
    val id: Int? = 0,
    val title: String? = "",
    @SerializedName("pdf_file")
    val url: String? = "",
    val date: String? = "",
): Parcelable