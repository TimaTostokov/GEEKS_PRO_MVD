package com.mvdasker.geeks_pro_mvd.data.remote.model.constitution

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConstitutionsChapter(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("chapter")
    val chapter: String? = null,
    @SerializedName("article")
    val article: String? = null
) : Parcelable