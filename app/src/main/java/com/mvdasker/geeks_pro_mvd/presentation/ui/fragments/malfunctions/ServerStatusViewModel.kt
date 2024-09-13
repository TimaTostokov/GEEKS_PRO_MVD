package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Constants.BASE_URL
import com.mvdasker.geeks_pro_mvd.common.ServerStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ServerStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val _serverStatus = MutableStateFlow<ServerStatus>(ServerStatus.UNAVAILABLE)
    val serverStatus: StateFlow<ServerStatus> = _serverStatus

    private val _navigateToMalfunctions = MutableStateFlow(false)
    val navigateToMalfunctions: StateFlow<Boolean> = _navigateToMalfunctions

    private var serverStatusCheckJob: Job? = null
    private val checkIntervalMillis = 3000L
    private var isCheckingEnabled = true

    fun startCheckingServerStatus() {
        stopCheckingServerStatus()
        serverStatusCheckJob = viewModelScope.launch {
            while (isActive && isCheckingEnabled) {
                val status = checkServerStatus()
                _serverStatus.value = status
                delay(checkIntervalMillis)
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

        return withContext(Dispatchers.IO) {
            try {
                val url = URL(BASE_URL)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "HEAD"
                    connectTimeout = 3000
                    connect()
                }
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    ServerStatus.AVAILABLE
                } else {
                    postNavigateToMalfunctions()
                    ServerStatus.UNAVAILABLE
                }
            } catch (e: IOException) {
                postNavigateToMalfunctions()
                ServerStatus.UNAVAILABLE
            }
        }
    }

    private fun postNavigateToMalfunctions() {
        _navigateToMalfunctions.value = true
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