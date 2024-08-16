package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import com.google.gson.annotations.SerializedName

data class Authorization(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)