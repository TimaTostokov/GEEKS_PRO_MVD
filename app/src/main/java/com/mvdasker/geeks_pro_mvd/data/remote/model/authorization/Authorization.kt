package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authorization(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null
) : Parcelable
