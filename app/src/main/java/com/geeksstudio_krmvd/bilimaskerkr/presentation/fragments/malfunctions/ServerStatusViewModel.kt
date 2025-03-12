package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.malfunctions

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Constants.BASE_URL
import com.geeksstudio_krmvd.bilimaskerkr.common.ServerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class ServerStatusViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val _serverStatus = MutableStateFlow(ServerStatus.AVAILABLE)
    val serverStatus: StateFlow<ServerStatus> = _serverStatus

    var wasServerUnavailable: Boolean = false
        private set

    private var serverStatusCheckJob: Job? = null
    private val checkIntervalMillis = 2000L
    private var connectTimeoutMillis = 2000
    private var isCheckingEnabled = true

    init {
        registerNetworkCallback()
        startCheckingServerStatus()
    }

    private fun registerNetworkCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    updateNetworkStatus(true)
                }

                override fun onLost(network: Network) {
                    updateNetworkStatus(false)
                }
            })

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        val isConnectedNow =
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        _isConnected.value = isConnectedNow
    }

    private fun updateNetworkStatus(isConnected: Boolean) {
        _isConnected.value = isConnected
    }

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

    private fun stopCheckingServerStatus() {
        serverStatusCheckJob?.cancel()
        serverStatusCheckJob = null
    }

    private suspend fun checkServerStatus(): ServerStatus {
        if (!_isConnected.value) {
            wasServerUnavailable = true
            return ServerStatus.NO_INTERNET
        }

        return withContext(Dispatchers.IO) {
            try {
                val url = URL(BASE_URL)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "HEAD"
                    connectTimeout = connectTimeoutMillis
                    connect()
                }
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    wasServerUnavailable = false
                    ServerStatus.AVAILABLE
                } else {
                    wasServerUnavailable = true
                    ServerStatus.UNAVAILABLE
                }
            } catch (e: IOException) {
                wasServerUnavailable = true
                ServerStatus.UNAVAILABLE
            } catch (e: Exception) {
                wasServerUnavailable = true
                ServerStatus.UNAVAILABLE
            }
        }
    }

    fun resetServerUnavailableFlag() {
        wasServerUnavailable = false
    }

    override fun onCleared() {
        stopCheckingServerStatus()
        super.onCleared()
    }

}