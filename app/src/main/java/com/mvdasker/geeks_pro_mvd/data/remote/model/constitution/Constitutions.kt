package com.mvdasker.geeks_pro_mvd.data.remote.model.constitution

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Constitutions(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("section")
    val section: String? = null,
    @SerializedName("chapters")
    val chapters: List<ConstitutionsChapter>
): Parcelable
