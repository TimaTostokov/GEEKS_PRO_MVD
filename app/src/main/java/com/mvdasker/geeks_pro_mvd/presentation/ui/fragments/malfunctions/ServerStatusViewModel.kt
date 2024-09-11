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
import com.mvdasker.geeks_pro_mvd.common.ServerStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ServerStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val _serverStatus = MutableLiveData<ServerStatus>()
    val serverStatus: LiveData<ServerStatus> = _serverStatus

    private val _navigateToMalfunctions = MutableLiveData<Boolean>()
    val navigateToMalfunctions: LiveData<Boolean> = _navigateToMalfunctions

    private var serverStatusCheckJob: Job? = null
    private val checkInterval = 3000L
    private var isCheckingEnabled = true

    fun startCheckingServerStatus() {
        stopCheckingServerStatus()
        serverStatusCheckJob = viewModelScope.launch {
            while (isActive && isCheckingEnabled) {
                _serverStatus.postValue(checkServerStatus())
                delay(checkInterval)
            }
        }
    }

    fun stopCheckingServerStatus() {
        serverStatusCheckJob?.cancel()
        serverStatusCheckJob = null
    }

    fun enableServerStatusCheck() {
        isCheckingEnabled = true
        startCheckingServerStatus()
    }

    fun disableServerStatusCheck() {
        isCheckingEnabled = false
        stopCheckingServerStatus()
    }

    private suspend fun checkServerStatus(): ServerStatus {
        if (!isInternetAvailable()) return ServerStatus.NO_INTERNET

        return try {
            withContext(Dispatchers.IO) {
                val url = URL("http://209.38.228.54:83/api/v1/")
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "HEAD"
                    connectTimeout = 3000
                    connect()
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        ServerStatus.AVAILABLE
                    } else {
                        withContext(Dispatchers.Main) {
                            _navigateToMalfunctions.postValue(true)
                        }
                        ServerStatus.UNAVAILABLE
                    }
                }
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                _navigateToMalfunctions.postValue(true)
            }
            ServerStatus.UNAVAILABLE
        }
    }

    @SuppressLint("NewApi")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

    fun resetNavigationFlag() {
        _navigateToMalfunctions.value = false
    }

}