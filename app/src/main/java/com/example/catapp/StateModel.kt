package com.example.catapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

sealed class State {
    object Error : State()
    object Loading : State()
    object Success : State()

}

abstract class StateModel {
//    protected var currentState: State = State.Success

    abstract val isError: LiveData<Boolean>
    abstract val isLoading : LiveData<Boolean>
    abstract  val isSuccess : LiveData<Boolean>

    abstract fun activateLoadingState()

    abstract fun activateSuccessState()

    abstract fun activateErrorState()
}

class DefaultStateModel @Inject constructor() : StateModel() {
    private val _isError = MutableLiveData<Boolean>(false)
    override val isError: LiveData<Boolean>
        get() = _isError

    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isSuccess = MutableLiveData<Boolean>(false)

    override val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    override fun activateLoadingState() {
        _isError.postValue(false)
        _isSuccess.postValue(false)

        _isLoading.postValue(true)
    }

    override fun activateSuccessState() {
        _isSuccess.postValue(true)

        _isLoading.postValue(false)
        _isError.postValue(false)
    }

    override fun activateErrorState() {
        _isError.postValue(true)

        _isLoading.postValue(false)
        _isSuccess.postValue(false)
    }


}
