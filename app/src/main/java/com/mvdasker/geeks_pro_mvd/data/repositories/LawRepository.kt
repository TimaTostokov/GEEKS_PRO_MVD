package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsChapter
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
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