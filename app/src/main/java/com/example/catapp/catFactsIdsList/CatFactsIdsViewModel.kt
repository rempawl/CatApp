package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.Result
import com.example.catapp.utils.providers.DispatcherProvider


abstract class CatFactsIdsViewModel(protected val dispatcherProvider: DispatcherProvider) :
    ViewModel() {

    abstract val result: LiveData<Result<*>>
    abstract fun fetchData()

}
