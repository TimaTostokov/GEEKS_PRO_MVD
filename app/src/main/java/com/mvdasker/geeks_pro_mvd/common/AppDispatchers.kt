package com.mvdasker.geeks_pro_mvd.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val default: CoroutineDispatcher = Dispatchers.Default,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)