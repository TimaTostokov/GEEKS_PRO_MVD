package com.mvdasker.geeks_pro_mvd.data.remote.model.news

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(
    @DrawableRes
    @SerializedName("id")
    val image: List<Int>,
    @SerializedName("urgent_news")
    val urgentNews: String? = null,
    @SerializedName("data")
    val data: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
) : Parcelable