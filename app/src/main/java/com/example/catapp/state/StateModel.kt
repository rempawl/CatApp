package com.example.catapp.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Error
import javax.inject.Inject
import kotlin.Exception


sealed class State<T>{
    data class Success<T>(val data : T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()
    object Loading : State<Any>()

    fun isSuccess(): Boolean = this is Success
    fun isLoading() : Boolean = this is Loading
    fun isError() : Boolean = this is Error

}

abstract class StateModel {

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
