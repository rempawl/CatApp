package com.example.catapp.catFactDetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.ErrorModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.formatDate
import com.example.catapp.utils.SchedulerProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

//class DefaultCatFactDetailsViewModel @AssistedInject constructor(
//    catFactRepository: CatFactRepository,
//    @Assisted factId: String,
//    schedulerProvider: SchedulerProvider,
//    errorModel: ErrorModel
//) : CatFactDetailsViewModel(catFactRepository, factId, schedulerProvider, errorModel) {
//
//    @AssistedInject.Factory
//    interface Factory {
//        fun create(factId: String): DefaultCatFactDetailsViewModel
//    }
//
//
//}

 class CatFactDetailsViewModel  @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val schedulerProvider: SchedulerProvider,
    val errorModel: ErrorModel
) : ViewModel() {

        @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): CatFactDetailsViewModel
    }

    private val disposables = CompositeDisposable()

    private val _catFactDetail = MutableLiveData<CatFact>()
    val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    fun refresh() {
        _catFactDetail.value = null
        fetchData()
    }

    fun init() {
        fetchData()
        _wasInitialLoadPerformed.value = true
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchData() {
        _isLoading.value = true
        val data = getData()

        subscribeData(
            data, ioScheduler = schedulerProvider.getIOScheduler(),
            uiScheduler = schedulerProvider.getUIScheduler()
        )
    }


    private fun subscribeData(
        data: Single<CatFact>,
        ioScheduler: Scheduler,
        uiScheduler: Scheduler
    ) {

        val d = data
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .doOnTerminate { _isLoading.postValue(false) }
            .map { fact -> fact.copy(updatedAt = fact.formatDate()) }
            .subscribe(
                { d -> onSuccess(d) },
                { onError() }
            )


        disposables.add(d)
    }


    private fun onError() {
        errorModel.activateError()
    }

    private fun onSuccess(items: CatFact) {
        _catFactDetail.postValue(items)
        errorModel.deactivateError()
    }


    private fun getData(): Single<CatFact> {
        return catFactRepository.getCatFact(factId)
    }
}

