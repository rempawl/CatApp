package com.example.catapp.catFactsIdsList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.ErrorModel
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
    errorModel: ErrorModel
) : CatFactsIdsViewModel(
    errorModel
) {
    private val disposables = CompositeDisposable()

    private val _factsIds = MutableLiveData<List<CatFactId>>()
    override val factsIds: LiveData<List<CatFactId>>
        get() = _factsIds

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    override val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed

    private val _isLoading = MutableLiveData(true)
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

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
        _isLoading.value = true
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
            .doOnTerminate { _isLoading.postValue(false) }
            .subscribe(
                { d -> onSuccess(d) },
                { onError() }
            )


        disposables.add(d)
    }


    private fun onError() {
        errorModel.activateError()
    }
    private fun onSuccess(items: List<CatFactId>) {
        _factsIds.postValue(items)
        errorModel.deactivateError()
    }

    private fun getData(): Single<List<CatFactId>> {
        return catFactRepository.getCatFactsIds()
    }


}

class FakeFactsIdsViewModel(errorModel: ErrorModel) : CatFactsIdsViewModel(errorModel) {
    companion object {
        const val ID_1 = "123"
        const val ID_2 = "345"
    }

    private val _fakeFactsIds = MutableLiveData<List<CatFactId>>(
        listOf(
            CatFactId(ID_1),
            CatFactId(ID_2)
        )
    )

    override val factsIds: LiveData<List<CatFactId>>
        get() = _fakeFactsIds

    private val _wasInitialLoadPerformed = MutableLiveData<Boolean>(false)
    override    val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed

    private val _isLoading = MutableLiveData(false)
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun refresh() {
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.postValue(true)
            delay(1000)
            _isLoading.postValue(false)
        }
    }

    override fun init() {
        _wasInitialLoadPerformed.postValue(true)
    }

}

abstract class CatFactsIdsViewModel constructor(
    val errorModel: ErrorModel
) : ViewModel() {

    abstract val factsIds: LiveData<List<CatFactId>>
    abstract val wasInitialLoadPerformed: LiveData<Boolean>

    abstract val isLoading: LiveData<Boolean>



    abstract fun refresh()

    abstract fun init()


}
