package com.mvdasker.geeks_pro_mvd.data.remote.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class DataItem(
    @DrawableRes
    val image:List<Int>,
    val urgentNews: String,
    val data: String,
    val tittle: String,
    val description: String,
) : Serializable