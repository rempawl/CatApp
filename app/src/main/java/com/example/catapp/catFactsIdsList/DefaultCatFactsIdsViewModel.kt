package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.data.Result
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.utils.providers.DispatcherProvider
import com.example.catapp.utils.subscribers.CoroutineSubscriber
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultCatFactsIdsViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val errorModel: ErrorModel,
    private val dispatcherProvider: DispatcherProvider

) : CatFactsIdsViewModel(),
    CoroutineSubscriber<List<CatFactId>> {

    private val _factsIds = MutableLiveData<List<CatFactId>>()

    private val _result = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _result

    override val factsIds: LiveData<List<CatFactId>>
        get() = _factsIds


    override fun refresh() {
        _factsIds.value = null
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _result.value = (Result.Loading)

        val data = catFactRepository.getCatFactsIdsAsync()
        viewModelScope.launch(dispatcherProvider.provideMainDispatcher()) {
            subscribeData(data)
        }

    }

    override fun onError(e: Throwable) {
        val message = errorModel.getErrorMessage(e)
        _result.value = (Result.Error(message))
    }

    override fun onSuccess(result: List<CatFactId>) {
        _factsIds.value = (result)
        _result.value = (Result.Success(result))
    }


}
