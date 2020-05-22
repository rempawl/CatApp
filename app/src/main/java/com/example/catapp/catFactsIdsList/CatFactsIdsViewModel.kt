package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.Result


abstract class CatFactsIdsViewModel : ViewModel() {

    abstract val result: LiveData<Result<*>>
    abstract fun fetchData()

}
