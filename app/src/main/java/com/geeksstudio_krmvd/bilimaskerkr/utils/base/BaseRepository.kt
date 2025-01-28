package com.geeksstudio_krmvd.bilimaskerkr.utils.base

import com.geeksstudio_krmvd.bilimaskerkr.common.Either
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