package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.common.Either
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ManagementsKrRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

   private val getData = mutableListOf<Governance>()

    fun fetchConstitutionsKr(): Flow<Either<Throwable, List<Governance>>> {
        return doRequest {
            sanaripAskerApi.fetchConstitutionsKr()
        }.flowOn(dispatchers.io)
    }

    suspend fun fetchConstitutionsVVKr(jobTittle: String? = null): List<Governance>{
        return sanaripAskerApi.fetchConstitutionsVVKr(jobTittle)
    }

    suspend fun fetchConstitutionsMIAKr(jobTittle: String? = null): List<Governance>{
        return sanaripAskerApi.fetchConstitutionsMVDKr(jobTittle)
    }

    fun getData():List<Governance> {
        getData.add(
            Governance(
                0,
                "dcscsxcsdc",
                "https://i.pinimg.com/236x/7e/d3/59/7ed3594f63952319213aad175fddd206.jpg",
                "rukia",
                "shinigami"
            ))
        return getData
    }

}