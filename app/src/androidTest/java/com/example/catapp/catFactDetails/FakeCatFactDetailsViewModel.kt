package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.Result
import com.example.catapp.utils.Utils
import com.example.catapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FakeCatFactDetailsViewModel(dispatcherProvider: DispatcherProvider) :
    CatFactDetailsViewModel(dispatcherProvider) {

    companion object {
        var shouldMockError = true
    }


    private val coroutineScope = CoroutineScope(dispatcherProvider.provideMainDispatcher())

    //todo mock error model

    init {
        fetchData()
    }


    private val _result = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _result

    override fun fetchData() {
        coroutineScope.launch {
            _result.value = Result.Loading
            delay(1000)
            _result.value =
                if (shouldMockError) Result.Error("error") else Result.Success(Utils.TEST_CAT_FACT_MAPPED)

        }

    }

}
