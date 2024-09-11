package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Base64
import android.util.Log
import com.mvdasker.geeks_pro_mvd.common.LanguagePreference
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.User
import org.json.JSONObject
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider,
    private val languagePreference: LanguagePreference,
) {

    private fun getUserIdFromToken(token: String): Int =
        JSONObject(String(Base64.decode(token.split(".")[1], Base64.URL_SAFE)))
            .getInt("user_id")

    suspend fun getUserId(): User? {
        val id = getUserIdFromToken(userProvider.accessToken)

        return id.run {
            try {
                Log.d("ololo", "Использовал id: $this")
                sanaripAskerApi.getUserById(userProvider.accessToken, this).also { user ->
                    user?.username?.let { userProvider.saveUserName(it) }
                    user?.img?.let { userProvider.saveUserPhoto(it) }
                }
            } catch (e: Exception) {
                Log.d("ololo", "Чтото пошло не так: $e")
                User(this, userProvider.getUserName(), userProvider.getUserPhoto())
            }
        }
    }

    fun saveSelectedLanguage(languageCode: String) {
        languagePreference.saveLanguage(languageCode)
    }

    fun getSavedLanguage(): String {
        return languagePreference.getLanguage ?: "ru"
    }

}