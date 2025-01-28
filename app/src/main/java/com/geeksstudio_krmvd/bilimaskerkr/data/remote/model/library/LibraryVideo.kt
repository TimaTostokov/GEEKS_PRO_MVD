package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.library

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryVideo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("embed_code")
    val video: String? = null
) : Parcelable
