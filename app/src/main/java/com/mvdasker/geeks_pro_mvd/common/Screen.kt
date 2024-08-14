package com.mvdasker.geeks_pro_mvd.common

sealed class Screen {
    data object Constitution : Screen()
    data object Law : Screen()
    data object Statutes : Screen()
    data object Notifications : Screen()
}