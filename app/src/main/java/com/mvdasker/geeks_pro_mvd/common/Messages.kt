package com.mvdasker.geeks_pro_mvd.common

sealed interface Messages {
    data object NetworkIsDisconnected : Messages
    data object ShowProgressBar: Messages
    data object HideProgressBar: Messages
}