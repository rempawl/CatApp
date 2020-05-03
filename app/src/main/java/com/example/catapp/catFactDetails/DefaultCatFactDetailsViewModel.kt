package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.state.State
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable

class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val schedulerProvider: SchedulerProvider
) : CatFactDetailsViewModel(), SingleSubscriber<CatFact> {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }

    private val disposables = CompositeDisposable()

    private val _catFactDetail = MutableLiveData<CatFact>()

    private val _state = MutableLiveData<State<*>>()
    override val state: LiveData<State<*>>
        get() = _state

    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun refresh() {
        _catFactDetail.postValue( null)
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _state.postValue(  State.Loading)

        val data = catFactRepository.getCatFact(factId)

        disposables.add(subscribeData(data, schedulerProvider))

    }


    override fun onError(e: Throwable) {
        _state.postValue( State.Error(e))
    }

    override fun onSuccess(data: CatFact) {
        _catFactDetail.postValue(  data)
        _state.postValue(  State.Success(data))
    }

}
