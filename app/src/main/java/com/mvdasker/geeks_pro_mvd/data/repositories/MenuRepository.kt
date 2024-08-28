package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Base64
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.User
import org.json.JSONObject
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider
) {

    private fun getUserIdFromToken(token: String): Int =
        JSONObject(String(Base64.decode(token.split(".")[1], Base64.URL_SAFE)))
            .getInt("user_id")

    suspend fun getUserId(): User? {
        val id = userProvider.getAccess()?.let { getUserIdFromToken(it) }

            return id?.run {
                try {
                    sanaripAskerApi.getUserById(this).also { user ->
                        user?.username?.let { userProvider.saveUserName(it) }
                        user?.img?.let { userProvider.saveUserPhoto(it) }
                    }
                } catch (e: Exception) {
                    User(this, userProvider.getUserName(), userProvider.getUserPhoto())
                }
            }
    }

}