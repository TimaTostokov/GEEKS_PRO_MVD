package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.utils.AppDispatchers
import com.mvdasker.geeks_pro_mvd.utils.Either
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ManagementsKrRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

    fun fetchConstitutionsKr(): Flow<Either<Throwable, List<Governance>>> {
        return doRequest {
            sanaripAskerApi.fetchConstitutionsKr()
        }.flowOn(dispatchers.io)
    }

    fun fetchConstitutionsVVKr(): Flow<Either<Throwable, List<Governance>>> {
        return doRequest {
            sanaripAskerApi.fetchConstitutionsVVKr()
        }.flowOn(dispatchers.io)
    }
}