package com.example.catapp.catFactDetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.state.StateModel
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
     fun fetchData() {
        stateModel.activateLoadingState()

        val data = getData()

        disposables.add(subscribeData(data, schedulerProvider))

    }

/*
    private fun subscribeData(
        data: Single<CatFact>,
        ioScheduler: Scheduler,
        uiScheduler: Scheduler
    ) {
        val d = data
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                { d ->
                    onSuccess(d)
                },
                { onError() }
            )

        disposables.add(d)
    }
*/

    override fun onError(e: Throwable) {
        stateModel.activateErrorState()
    }

    override fun onSuccess(data: CatFact) {
        _catFactDetail.value = data
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
        var shouldMockError = true

    }

    private val _catFactDetail = MutableLiveData(FAKE_FACT)
    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail


    override fun refresh() {

    }

    init {

        if (shouldMockError) {
            mockError()
        } else {
            stateModel.activateLoadingState()

            stateModel.activateSuccessState()
        }


    }

    private fun mockError() {
        stateModel.activateErrorState()
    }

}

abstract class CatFactDetailsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val catFactDetail: LiveData<CatFact>

    abstract fun refresh()


}

