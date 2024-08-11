package com.mvdasker.geeks_pro_mvd.data.remote.model.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("month")
    val month: String? = null,
    @SerializedName("selection")
    val selection: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("created_at")
    val createAt: String? = null,
    @SerializedName("is_read")
    val isRead: Boolean? = false
) : Parcelable