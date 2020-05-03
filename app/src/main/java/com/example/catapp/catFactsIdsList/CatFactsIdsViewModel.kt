package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactId
import com.example.catapp.state.State
import javax.inject.Inject


abstract class CatFactsIdsViewModel @Inject constructor(): ViewModel() {

    abstract val state: LiveData<State<*>>
    abstract val factsIds: LiveData<List<CatFactId>>
    abstract fun refresh()


}
