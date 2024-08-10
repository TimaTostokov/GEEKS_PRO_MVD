package com.mvdasker.geeks_pro_mvd.data.remote.model.labrary

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class NotePro(
    val id: Int? = null,
    val title: String,
    val description: String,
    @DrawableRes
    val image: Int,
    val text: String
): Parcelable