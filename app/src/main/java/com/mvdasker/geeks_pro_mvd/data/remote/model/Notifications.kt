package com.mvdasker.geeks_pro_mvd.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notifications(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createAt: String,
    @SerializedName("is_read")
    val isRead: Boolean
) : Serializable