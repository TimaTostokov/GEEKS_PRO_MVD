package com.mvdasker.geeks_pro_mvd.common

sealed class ServerStatus {
    data object AVAILABLE : ServerStatus()
    data object UNAVAILABLE : ServerStatus()
    data object NO_INTERNET : ServerStatus()
}