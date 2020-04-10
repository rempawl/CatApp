package com.example.catapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

sealed class ErrorState {
    object Active : ErrorState()
    object Inactive : ErrorState()
}

abstract class ErrorModel {
    protected var currentErrorState: ErrorState = ErrorState.Inactive

    abstract val isErrorActive: LiveData<Boolean>

    abstract fun activateError()
    abstract fun deactivateError()
}

class DefaultErrorModel @Inject constructor() : ErrorModel() {
    private val _isErrorActive = MutableLiveData<Boolean>(false)
    override val isErrorActive: LiveData<Boolean>
        get() = _isErrorActive

    override fun activateError() {
        if(currentErrorState is ErrorState.Inactive) {
            _isErrorActive.postValue(true)
            currentErrorState = ErrorState.Active
        }

    }

    override fun deactivateError() {
        if (currentErrorState is ErrorState.Active) {
            _isErrorActive.postValue(false)
            currentErrorState = ErrorState.Inactive
        }

    }

}
