package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.formatUpdateDate
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.utils.providers.DispatcherProvider
import com.example.catapp.utils.subscribers.CoroutineSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout


class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val errorModel: ErrorModel,
     dispatcherProvider: DispatcherProvider
) : CatFactDetailsViewModel(dispatcherProvider), CoroutineSubscriber<CatFact> {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }


    private val _result = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _result


    init {
        fetchData()
    }

    override fun fetchData() {
        viewModelScope.launch(dispatcherProvider.provideMainDispatcher()) {
            _result.postValue(Result.Loading)

            val data = withTimeout(5_000) {
                catFactRepository.getCatFactAsync(factId)
            }
            subscribeData(data)
        }
    }


    override fun onError(e: Throwable) {
        val message = errorModel.getErrorMessage(e)
        _result.value = (Result.Error(message))
    }

    override fun onSuccess(result: CatFact) {
        val fact = Result.Success(result.copy(updatedAt = result.formatUpdateDate()))
        _result.value = fact
    }


}
