package com.mvdasker.geeks_pro_mvd.data.remote.model.law

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LawsCharter(
    @SerializedName("charter")
    val charter: String? = null,
    @SerializedName("article")
    val article: String? = null
) : Parcelable