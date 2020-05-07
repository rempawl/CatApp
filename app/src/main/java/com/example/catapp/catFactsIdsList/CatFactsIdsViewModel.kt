package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.State


abstract class CatFactsIdsViewModel : ViewModel() {

    abstract val state: LiveData<State<*>>
    abstract val factsIds: LiveData<List<CatFactId>>
    abstract fun refresh()


}
