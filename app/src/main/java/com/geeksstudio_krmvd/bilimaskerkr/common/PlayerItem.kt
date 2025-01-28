package com.geeksstudio_krmvd.bilimaskerkr.common

sealed class PlayerItem(private val url: String) {
    data class Video(val videoUrl: String) : PlayerItem(videoUrl)
    data class Image(val imageUrl: String) : PlayerItem(imageUrl)
}