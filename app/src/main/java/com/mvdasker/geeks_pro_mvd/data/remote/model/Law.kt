package com.mvdasker.geeks_pro_mvd.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Law(
    val id: Int? = 0,
    val title: String? = "",
    val text: String = ""
): Parcelable