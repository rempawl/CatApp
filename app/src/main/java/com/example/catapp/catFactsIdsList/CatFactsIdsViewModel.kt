package com.example.catapp.catFactsIdsList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.state.StateModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.utils.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultCatFactsIdsViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val schedulerProvider: SchedulerProvider,
    stateModel: StateModel
) : CatFactsIdsViewModel(
    stateModel
) {
    private val disposables = CompositeDisposable()
    private val _factsIds = MutableLiveData<List<CatFactId>>()
    override val factsIds: LiveData<List<CatFactId>>
        get() = _factsIds

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    override val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun refresh() {
        _factsIds.value = null
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
        data: Single<List<CatFactId>>,
        ioScheduler: Scheduler,
        uiScheduler: Scheduler
    ) {
        val d = data
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(
                { d -> onSuccess(d) },
                { onError() }
            )


        disposables.add(d)
    }


    private fun onError() {
        stateModel.activateErrorState()
    }

    private fun onSuccess(items: List<CatFactId>) {
        _factsIds.postValue(items)
        stateModel.activateSuccessState()
    }

    private fun getData(): Single<List<CatFactId>> {
        return catFactRepository.getCatFactsIds()
    }


}

class FakeFactsIdsViewModel(stateModel: StateModel) : CatFactsIdsViewModel(stateModel) {
    companion object {
        const val ID_1 = "123"
        const val ID_2 = "345"
        var SHOULD_MOCK_ERROR = true

    }

    private val _fakeFactsIds = MutableLiveData<List<CatFactId>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override val factsIds: LiveData<List<CatFactId>>
        get() = _fakeFactsIds

    private val _wasInitialLoadPerformed = MutableLiveData<Boolean>(false)
    override val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed


    override fun refresh() {
        coroutineScope.launch {
            stateModel.activateLoadingState()
            delay(1000)
            stateModel.activateSuccessState()
        }
    }

    override fun init() {
        coroutineScope.launch {
            if(SHOULD_MOCK_ERROR) {
                mockError()
            }else {

                stateModel.activateLoadingState()
                _fakeFactsIds.postValue(listOf(CatFactId(ID_1), CatFactId(ID_2)))
                delay(100)
                _wasInitialLoadPerformed.postValue(true)
                stateModel.activateSuccessState()
            }
        }
    }


    private fun mockError() {
//        coroutineScope.launch {
//            stateModel.activateLoadingState()
//            delay(100)
            stateModel.activateErrorState()
//        }
    }

}

abstract class CatFactsIdsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val factsIds: LiveData<List<CatFactId>>
    abstract val wasInitialLoadPerformed: LiveData<Boolean>
    abstract fun refresh()
    abstract fun init()



}
