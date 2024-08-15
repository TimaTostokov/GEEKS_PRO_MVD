package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import com.google.gson.annotations.SerializedName

data class Authorization(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)