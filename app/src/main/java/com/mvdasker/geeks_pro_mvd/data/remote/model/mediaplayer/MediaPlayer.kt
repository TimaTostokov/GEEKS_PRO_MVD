package com.mvdasker.geeks_pro_mvd.data.remote.model.mediaplayer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaPlayer(
    @SerializedName("video")
    val video: String? = null,
) : Parcelable