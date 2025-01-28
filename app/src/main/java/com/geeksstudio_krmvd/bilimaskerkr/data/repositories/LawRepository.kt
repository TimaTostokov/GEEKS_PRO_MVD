package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.common.AppDispatchers
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.law.LawsChapter
import com.geeksstudio_krmvd.bilimaskerkr.utils.base.BaseRepository
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LawRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

    fun getLaw() = doRequest {
        sanaripAskerApi.getLaw()
    }.flowOn(dispatchers.io)

    suspend fun getLawById(id: Int): Result<LawsChapter> = runCatching {
        withContext(dispatchers.io) {
            sanaripAskerApi.getLawById(id)
        }
    }

}