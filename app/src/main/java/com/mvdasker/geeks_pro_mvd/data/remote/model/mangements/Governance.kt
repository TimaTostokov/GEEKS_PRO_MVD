package com.mvdasker.geeks_pro_mvd.data.remote.model.mangements

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Governance(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("photo")
    val photo: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("job_tittle")
    val jobTittle: String? = null,
    @SerializedName("job_title_ru")
    val jobTitleRu: String? = null,
    @SerializedName("job_title_ky")
    val jobTitleKy: String? = null
): Parcelable