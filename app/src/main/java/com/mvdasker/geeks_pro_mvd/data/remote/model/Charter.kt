package com.mvdasker.geeks_pro_mvd.data.remote.model

import com.google.gson.annotations.SerializedName

data class Charter(
    val id: Int? = 0,
    val title: String? = "",
    @SerializedName("pdf_file")
    val url: String? = "",
    val date: String? = "",
)