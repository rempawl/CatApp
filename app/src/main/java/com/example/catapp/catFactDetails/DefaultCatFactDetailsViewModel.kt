package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.formatUpdateDate
import com.example.catapp.utils.State
import com.example.catapp.utils.subscribers.CoroutineSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String
//    private val schedulerProvider: SchedulerProvider
) : CatFactDetailsViewModel(),
    CoroutineSubscriber<CatFact> {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }

//    private val disposables = CompositeDisposable()

    private val _catFactDetail = MutableLiveData<CatFact>()

    private val _state = MutableLiveData<State<*>>()
    override val state: LiveData<State<*>>
        get() = _state

    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail


    override fun onCleared() {
        super.onCleared()
//        disposables.clear()
    }

    override fun refresh() {
        _catFactDetail.value = (null)
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _state.postValue(State.Loading)

        val data = catFactRepository.getCatFactAsync(factId)

        viewModelScope.launch {
            subscribeData(data)
        }

    }


    override fun onError(e: Throwable) {
        _state.value = (State.Error(e))
    }

    override fun onSuccess(data: CatFact) {
        val fact = State.Success(data.copy(updatedAt = data.formatUpdateDate()))

        _catFactDetail.value = (fact.data)
        _state.value = fact
    }


}
