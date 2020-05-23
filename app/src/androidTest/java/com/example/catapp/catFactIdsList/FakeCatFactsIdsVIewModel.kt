package com.example.catapp.catFactIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.utils.Utils
import com.example.catapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FakeFactsIdsViewModel(dispatcherProvider: DispatcherProvider) :
    CatFactsIdsViewModel(dispatcherProvider) {
    companion object {
        var shouldMockError = true
    }

    private val _fakeFactsIds = MutableLiveData<List<CatFactId>>()

    private val coroutineScope = CoroutineScope(dispatcherProvider.provideMainDispatcher())

    private val _result = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _result


    override fun fetchData() {
        coroutineScope.launch {
            _result.value = Result.Loading
            delay(1000)
            _result.value =
                if (shouldMockError) Result.Error("todo") else Result.Success(Utils.TEST_IDS)

        }
    }

    init {
        fetchData()
    }


}
