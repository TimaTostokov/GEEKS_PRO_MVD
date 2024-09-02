package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.io.IOException
import java.net.HttpURLConnection

import java.net.URL

class ServerStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val _serverStatus = MutableLiveData<ServerStatus>()
    val serverStatus: LiveData<ServerStatus> get() = _serverStatus

    private var serverStatusCheckJob: Job? = null
    private val checkInterval = 5000L

    fun startCheckingServerStatus() {
        stopCheckingServerStatus()
        serverStatusCheckJob = viewModelScope.launch {while (isActive) {
            val status = withContext(Dispatchers.IO) { checkServerStatus() }
            _serverStatus.postValue(status)
            delay(checkInterval)
        }
        }
    }

    fun stopCheckingServerStatus() {
        serverStatusCheckJob?.cancel()
        serverStatusCheckJob = null
    }

    private fun checkServerStatus(): ServerStatus {
        return if (isInternetAvailable()) {
            try {
                val url = URL("http://209.38.228.54:83/api/v1/")
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "HEAD"
                    connectTimeout = 5000
                    connect()
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        ServerStatus.AVAILABLE
                    } else {
                        ServerStatus.UNAVAILABLE
                    }
                }
            } catch (e: IOException) {
                ServerStatus.UNAVAILABLE
            }
        } else {
            ServerStatus.NO_INTERNET
        }
    }

    @SuppressLint("NewApi")
    private fun isInternetAvailable(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}

sealed class ServerStatus {
    data object AVAILABLE : ServerStatus()
    data object UNAVAILABLE : ServerStatus()
    data object NO_INTERNET : ServerStatus()
}