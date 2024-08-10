package com.mvdasker.geeks_pro_mvd.data.remote.model.mangements

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Governance(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("job_tittle")
    val jobTittle: String,
): Parcelable
