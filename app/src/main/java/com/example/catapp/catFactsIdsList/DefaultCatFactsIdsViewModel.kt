package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.state.State
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DefaultCatFactsIdsViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val schedulerProvider: SchedulerProvider
) : CatFactsIdsViewModel(), SingleSubscriber<List<CatFactId>> {

    private val disposables = CompositeDisposable()

    private val _factsIds = MutableLiveData<List<CatFactId>>()

    private val _state = MutableLiveData<State<*>>()
    override val state: LiveData<State<*>>
        get() = _state

    override val factsIds: LiveData<List<CatFactId>>
        get() = _factsIds


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun refresh() {
        _factsIds.value = null
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _state.value = State.Loading

        val data = catFactRepository.getCatFactsIds()

        disposables.add(subscribeData(data, schedulerProvider))
    }

    override fun onError(e: Throwable) {
        _state.value = State.Error(e)
    }

    override fun onSuccess(data: List<CatFactId>) {
        _factsIds.value = (data)
        _state.value = State.Success(data)
    }


}
