package com.geeksstudio_krmvd.bilimaskerkr.common

sealed interface Messages {
    data object NetworkIsDisconnected : Messages
    data object ShowProgressBar: Messages
    data object HideProgressBar: Messages
}