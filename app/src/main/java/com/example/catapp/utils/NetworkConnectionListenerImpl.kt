package com.example.catapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.network.NetworkCallback
import javax.inject.Inject

class NetworkConnectionListenerImpl @Inject constructor() : NetworkCallback.NetworkConnectionListener {

    private val _isConnectionActive = MutableLiveData<Boolean>()

    override val isConnectionActive: LiveData<Boolean>
        get() = _isConnectionActive

    override fun onInactive() {
        _isConnectionActive.postValue(false)
    }

    override fun onActive() {
        _isConnectionActive.postValue(true)
    }
}