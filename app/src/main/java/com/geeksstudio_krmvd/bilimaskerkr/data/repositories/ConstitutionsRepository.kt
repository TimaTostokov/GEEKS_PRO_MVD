package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.common.AppDispatchers
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.constitution.ConstitutionsChapter
import com.geeksstudio_krmvd.bilimaskerkr.utils.base.BaseRepository
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConstitutionsRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

    fun getConstitution() = doRequest {
        sanaripAskerApi.getConstitution()
    }.flowOn(dispatchers.io)

    suspend fun getConstitutionById(id: Int): Result<ConstitutionsChapter> = runCatching {
        withContext(dispatchers.io) {
            sanaripAskerApi.getConstitutionById(id)
        }
    }

}