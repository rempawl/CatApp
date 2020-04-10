package com.example.catapp.network

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import javax.inject.Inject

class NetworkCallback @Inject constructor(private val listener: NetworkConnectionListener)  : ConnectivityManager.NetworkCallback() {


    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener.onActive()
    }


    override fun onLost(network: Network) {
        super.onLost(network)
        listener.onInactive()
    }
    override fun onUnavailable() {
        super.onUnavailable()
        listener.onInactive()
    }

    interface NetworkConnectionListener {
        val isConnectionActive : LiveData<Boolean>
        fun onInactive()
        fun onActive()
    }

}