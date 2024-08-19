package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("photo")
    val photo: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
    @SerializedName("access")
    val access: String? = null
)