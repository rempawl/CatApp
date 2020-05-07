package com.example.catapp.data


sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Any>()

    fun isSuccess(): Boolean = this is Success
    fun isLoading() : Boolean = this is Loading
    fun isError() : Boolean = this is Error

}

