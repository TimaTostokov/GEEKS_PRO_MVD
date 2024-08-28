package com.mvdasker.geeks_pro_mvd.data.remote.model.image

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageSlider(
    @SerializedName("image")
    val image: String? = null,
) : Parcelable