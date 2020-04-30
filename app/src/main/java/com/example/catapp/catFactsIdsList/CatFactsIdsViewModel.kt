package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactId
import com.example.catapp.state.StateModel


abstract class CatFactsIdsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val factsIds: LiveData<List<CatFactId>>
    abstract fun refresh()


}
