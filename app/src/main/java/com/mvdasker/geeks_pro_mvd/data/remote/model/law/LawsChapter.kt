package com.mvdasker.geeks_pro_mvd.data.remote.model.law

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LawsChapter(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("chapter")
    val chapter: String? = null,
    @SerializedName("article")
    val article: String? = null
) : Parcelable