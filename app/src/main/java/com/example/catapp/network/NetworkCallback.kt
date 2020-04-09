package com.example.catapp.network

import android.net.ConnectivityManager
import android.net.Network

class NetworkCallback(private val listener: NetworkChangeListener) : ConnectivityManager.NetworkCallback() {


    override fun onLost(network: Network) {
        super.onLost(network)
        listener.onInactive()
    }
    override fun onUnavailable() {
        super.onUnavailable()
        listener.onInactive()
    }

    interface NetworkChangeListener {
        fun onInactive()
    }

}