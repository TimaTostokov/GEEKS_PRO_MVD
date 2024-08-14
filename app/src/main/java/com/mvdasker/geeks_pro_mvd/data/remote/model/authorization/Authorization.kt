package com.mvdasker.geeks_pro_mvd.data.remote.model.authorization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authorization(
    val login: String,
    val password: String
) : Parcelable