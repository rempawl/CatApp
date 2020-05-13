package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.data.Result
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.entities.formatUpdateDate
import com.example.catapp.utils.providers.DispatcherProvider
import com.example.catapp.utils.subscribers.CoroutineSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch


class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val errorModel: ErrorModel,
    private val dispatcherProvider: DispatcherProvider
) : CatFactDetailsViewModel(), CoroutineSubscriber<CatFact> {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }


    private val _catFactDetail = MutableLiveData<CatFact>()
    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail

    private val _state = MutableLiveData<Result<*>>()
    override val result: LiveData<Result<*>>
        get() = _state


    override fun refresh() {
        _catFactDetail.value = (null)
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(dispatcherProvider.provideMainDispatcher()) {
            _state.postValue(Result.Loading)

            val data = catFactRepository.getCatFactAsync(factId)

            subscribeData(data)
        }
    }


    override fun onError(e: Throwable) {
        val message = errorModel.getErrorMessage(e)
        _state.value = (Result.Error(message))
    }

    override fun onSuccess(result: CatFact) {
        val fact = Result.Success(result.copy(updatedAt = result.formatUpdateDate()))

        _catFactDetail.value = (fact.data)
        _state.value = fact
    }


}
