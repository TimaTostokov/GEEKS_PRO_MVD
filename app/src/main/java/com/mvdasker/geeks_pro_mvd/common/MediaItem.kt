package com.mvdasker.geeks_pro_mvd.common

sealed class PlayerItem(private val url: String) {
    data class Video(val videoUrl: String) : PlayerItem(videoUrl)
    data class Image(val imageUrl: String) : PlayerItem(imageUrl)
}