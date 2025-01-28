package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.parent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParentModel(
    val title: String? = null,
    var isExpandable: Boolean = false
) : Parcelable