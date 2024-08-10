package com.mvdasker.geeks_pro_mvd.data.remote.model.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Notification(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createAt: String,
    @SerializedName("is_read")
    val isRead: Boolean
): Parcelable