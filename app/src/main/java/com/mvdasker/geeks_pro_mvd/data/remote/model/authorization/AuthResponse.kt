package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    @SerializedName("access")
    val access: String? = null,
    @SerializedName("refresh")
    val refresh: String? = null
): Parcelable
