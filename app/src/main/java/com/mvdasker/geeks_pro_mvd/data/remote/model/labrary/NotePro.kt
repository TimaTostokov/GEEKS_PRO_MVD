package com.mvdasker.geeks_pro_mvd.data.remote.model.labrary

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class NotePro(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @DrawableRes
    @SerializedName("image")
    val image: Int? = null,
    @SerializedName("text")
    val text: String? = null
) : Parcelable