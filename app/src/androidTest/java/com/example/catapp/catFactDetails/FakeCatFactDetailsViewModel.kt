package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFact
import com.example.catapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FakeCatFactDetailsViewModel(dispatcherProvider: DispatcherProvider) :
    CatFactDetailsViewModel(dispatcherProvider) {

    companion object {
        val FAKE_FACT = CatFact("TEXT", "DATE")
        var shouldMockError = true

    }


    private val coroutineScope = CoroutineScope(dispatcherProvider.provideMainDispatcher())


    init {
        coroutineScope.launch {

        }
    }


    private val _result = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _result

    override fun fetchData() {
        TODO("")
    }

}
