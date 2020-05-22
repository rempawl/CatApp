package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.Result


abstract class CatFactDetailsViewModel : ViewModel() {
    abstract val result: LiveData<Result<*>>

    abstract fun fetchData()
}

