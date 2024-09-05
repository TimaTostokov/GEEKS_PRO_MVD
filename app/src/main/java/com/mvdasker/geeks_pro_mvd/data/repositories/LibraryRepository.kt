package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider
) {

    suspend fun searchNotes(title: String? = null, description: String? = null): List<Library> {
        return sanaripAskerApi.getLibrary(userProvider.accessToken, title, description).reversed()
    }

    suspend fun getLibraryById(id: Int): Library =
        sanaripAskerApi.getLibraryById(userProvider.accessToken, id)

    suspend fun getIsNotReadNotif(): List<Notification> =
        sanaripAskerApi.getNotification(userProvider.accessToken)
}