package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("full_name")
    val username: String? = null,
    @SerializedName("image")
    val img: String? = null
) : Parcelable