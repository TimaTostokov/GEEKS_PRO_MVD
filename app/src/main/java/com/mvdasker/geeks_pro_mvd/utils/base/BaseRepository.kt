package com.mvdasker.geeks_pro_mvd.utils.base

import com.mvdasker.geeks_pro_mvd.utils.ext.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    protected fun <T> doRequest(
        request: suspend () -> T,
    ) = flow<Either<Throwable, T>> {
        emit(
            Either.Right(request())
        )
    }.flowOn(Dispatchers.IO).catch {
        emit(Either.Left(it))
    }
}