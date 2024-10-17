package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Constants.BASE_URL
import com.mvdasker.geeks_pro_mvd.common.ServerStatus
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

@HiltViewModel
class ServerStatusViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val _serverStatus =
        MutableStateFlow(savedStateHandle["serverStatus"] ?: ServerStatus.AVAILABLE)
    val serverStatus: StateFlow<ServerStatus> = _serverStatus

    private var serverStatusCheckJob: Job? = null
    private val checkIntervalMillis = 3000L
    private var isCheckingEnabled = true

    var wasServerUnavailable = savedStateHandle["wasServerUnavailable"] ?: false

    init {
        startCheckingServerStatus()
    }

    fun startCheckingServerStatus() {
        if (_serverStatus.value == ServerStatus.AVAILABLE) {
            return
        }

        stopCheckingServerStatus()

        serverStatusCheckJob = viewModelScope.launch {
            while (isActive && isCheckingEnabled) {
                val status = checkServerStatus()
                _serverStatus.value = status as ServerStatus.AVAILABLE
                delay(checkIntervalMillis)
            }
        }
    }

    private fun stopCheckingServerStatus() {
        serverStatusCheckJob?.cancel()
        serverStatusCheckJob = null
    }

    private suspend fun checkServerStatus(): ServerStatus {
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
                    wasServerUnavailable = true
                    ServerStatus.UNAVAILABLE
                }
            } catch (e: IOException) {
                Log.d("ServerStatus", "Server is unavailable due to exception: ${e.message}")
                wasServerUnavailable = true
                ServerStatus.UNAVAILABLE
            }
        }
    }

    fun resetServerUnavailableFlag() {
        wasServerUnavailable = false
    }

    override fun onCleared() {
        savedStateHandle["serverStatus"] = _serverStatus.value
        savedStateHandle["wasServerUnavailable"] = wasServerUnavailable
        super.onCleared()
    }

}