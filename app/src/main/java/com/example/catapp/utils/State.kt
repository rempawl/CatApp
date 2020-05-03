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

