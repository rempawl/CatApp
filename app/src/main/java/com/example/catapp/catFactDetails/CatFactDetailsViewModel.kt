package com.example.catapp.catFactDetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.StateModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.formatDate
import com.example.catapp.utils.SchedulerProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class DefaultCatFactDetailsViewModel @AssistedInject constructor(
    private val catFactRepository: CatFactRepository,
    @Assisted private val factId: String,
    private val schedulerProvider: SchedulerProvider,
    stateModel: StateModel
) : CatFactDetailsViewModel(stateModel) {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): DefaultCatFactDetailsViewModel
    }

    private val disposables = CompositeDisposable()

    private val _catFactDetail = MutableLiveData<CatFact>()
    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    override val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun refresh() {
        _catFactDetail.value = null
        fetchData()
    }

    override fun init() {
        fetchData()
        _wasInitialLoadPerformed.value = true
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchData() {

        stateModel.activateLoadingState()

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

            .map { fact -> fact.copy(updatedAt = fact.formatDate()) }
            .subscribe(
                { d ->
                    onSuccess(d)
                },
                { onError() }
            )



        disposables.add(d)
    }

    private fun onError() {
        stateModel.activateErrorState()
    }

    private fun onSuccess(items: CatFact) {
        _catFactDetail.postValue(items)
        stateModel.activateSuccessState()
    }

    private fun getData(): Single<CatFact> {
        return catFactRepository.getCatFact(factId)
    }

}

class FakeCatFactDetailsViewModel constructor(stateModel: StateModel) :
    CatFactDetailsViewModel(stateModel) {
    companion object {
        val FAKE_FACT = CatFact("TEXT", "DATE")

    }

    override val catFactDetail: LiveData<CatFact>
        get() {
            return MutableLiveData<CatFact>(FAKE_FACT)
        }
    private val _wasInitialLoadPerformed = MutableLiveData(false)
    override val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed


    override fun refresh() {

    }

    override fun init() {

    }

}

abstract class CatFactDetailsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val catFactDetail: LiveData<CatFact>
    abstract val wasInitialLoadPerformed: LiveData<Boolean>

    abstract fun refresh()
    abstract fun init()


}

