package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.state.StateModel
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DefaultCatFactsIdsViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val schedulerProvider: SchedulerProvider,
    stateModel: StateModel
) : CatFactsIdsViewModel(stateModel), SingleSubscriber<List<CatFactId>> {

    private val disposables = CompositeDisposable()

    private val _factsIds = MutableLiveData<List<CatFactId>>()
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
        stateModel.activateLoadingState()

        val data = catFactRepository.getCatFactsIds()

        disposables.add(subscribeData(data, schedulerProvider))
    }

    override fun onError(e: Throwable) {
        stateModel.activateErrorState()
    }

    override fun onSuccess(data: List<CatFactId>) {
        _factsIds.value = (data)
        stateModel.activateSuccessState()
    }


}
