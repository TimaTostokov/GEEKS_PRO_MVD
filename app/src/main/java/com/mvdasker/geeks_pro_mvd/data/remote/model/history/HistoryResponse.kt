package com.mvdasker.geeks_pro_mvd.data.remote.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResponse(
    val id: Int,
    val text: String,
    val text_ky: String,
    val text_ru: String,
    val title: String,
    val title_ky: String,
    val title_ru: String
): Parcelable