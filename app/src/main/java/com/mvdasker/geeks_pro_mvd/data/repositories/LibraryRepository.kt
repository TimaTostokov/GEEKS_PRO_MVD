package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import javax.inject.Inject

class LibraryRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun searchNotes(title: String? = null, description: String? = null): List<Library> {
        return sanaripAskerApi.getLibrary(title, description).reversed()
    }

    suspend fun getLibraryById(id: Int): Library = sanaripAskerApi.getLibraryById(id)
}