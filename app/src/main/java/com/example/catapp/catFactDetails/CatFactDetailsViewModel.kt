package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFact
import com.example.catapp.state.State
import javax.inject.Inject


abstract class CatFactDetailsViewModel @Inject constructor() : ViewModel() {

    abstract val state: LiveData<State<*>>
    abstract val catFactDetail: LiveData<CatFact>

    abstract fun refresh()


}

