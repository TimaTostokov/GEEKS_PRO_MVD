package com.mvdasker.geeks_pro_mvd.data.remote.model

import androidx.annotation.DrawableRes
import org.w3c.dom.Text
import java.io.Serializable

data class NotePro(
    val id: Int? = null,
    val title: String,
    val description: String,
    @DrawableRes
    val image:Int,
    val text:String
):Serializable
