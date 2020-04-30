package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFact
import com.example.catapp.state.StateModel


abstract class CatFactDetailsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val catFactDetail: LiveData<CatFact>

    abstract fun refresh()


}

