package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.library.Library
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.notification.Notification
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) {

    suspend fun searchNotes(title: String? = null, description: String? = null): List<Library> {
        return sanaripAskerApi.getLibrary(title, description).reversed()
    }

    suspend fun getLibraryById(id: Int): Library =
        sanaripAskerApi.getLibraryById(id)

    suspend fun getIsNotReadNotif(): List<Notification> =
        sanaripAskerApi.getNotification()
}