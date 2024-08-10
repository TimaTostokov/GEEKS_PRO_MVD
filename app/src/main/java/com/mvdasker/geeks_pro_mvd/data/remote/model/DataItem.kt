package com.mvdasker.geeks_pro_mvd.data.remote.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(
    @DrawableRes
    val image: List<Int>,
    val urgentNews: String,
    val data: String,
    val tittle: String,
    val description: String,
) : Parcelable
