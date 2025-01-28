package com.geeksstudio_krmvd.bilimaskerkr.common

sealed class Screen {
    data object Constitution : Screen()
    data object Law : Screen()
    data object Statutes : Screen()
    data object Notifications : Screen()
}