package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.common.Either
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.ConstitutionsChapter
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConstitutionsRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

    fun getConstitution() = doRequest {
        sanaripAskerApi.getConstitution(userProvider.accessToken)
    }.flowOn(dispatchers.io)


    suspend fun getConstitutionById(id: Int): Flow<Either<Throwable, ConstitutionsChapter>> =
        doRequest {
            sanaripAskerApi.getConstitutionById(userProvider.accessToken, id)
        }

}
