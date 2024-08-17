package com.mvdasker.geeks_pro_mvd.data.remote.model.news

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<News>,
)