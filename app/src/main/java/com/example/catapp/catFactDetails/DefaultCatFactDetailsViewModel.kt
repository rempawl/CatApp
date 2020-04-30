package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.state.StateModel
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable

class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val schedulerProvider: SchedulerProvider,
    stateModel: StateModel
) : CatFactDetailsViewModel(stateModel), SingleSubscriber<CatFact> {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }

    private val disposables = CompositeDisposable()

    private val _catFactDetail = MutableLiveData<CatFact>()
    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun refresh() {
        _catFactDetail.value = null
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        stateModel.activateLoadingState()

        val data = catFactRepository.getCatFact(factId)

        disposables.add(subscribeData(data, schedulerProvider))

    }


    override fun onError(e: Throwable) {
        stateModel.activateErrorState()
    }

    override fun onSuccess(data: CatFact) {
        _catFactDetail.value = data
        stateModel.activateSuccessState()
    }


}
