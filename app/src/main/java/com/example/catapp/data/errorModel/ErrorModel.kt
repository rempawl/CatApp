package com.example.catapp.data.errorModel

interface ErrorModel {
    fun getErrorMessage(exception: Throwable): String
}


