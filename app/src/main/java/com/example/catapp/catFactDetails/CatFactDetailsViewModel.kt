package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModel
import com.example.catapp.data.Result
import com.example.catapp.utils.providers.DispatcherProvider


abstract class CatFactDetailsViewModel(protected val dispatcherProvider: DispatcherProvider) : ViewModel() {
    abstract val result: LiveData<Result<*>>

    abstract fun fetchData()
}

