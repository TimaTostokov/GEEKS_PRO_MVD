package com.mvdasker.geeks_pro_mvd.data.remote.model.charter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Charter(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("pdf_file")
    val url: String? = null
): Parcelable