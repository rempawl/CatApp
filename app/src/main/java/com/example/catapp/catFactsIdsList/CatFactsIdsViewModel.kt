package com.example.catapp.catFactsIdsList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.state.StateModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.utils.SchedulerProvider
import com.example.catapp.utils.SingleSubscriber
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
     fun fetchData() {
        stateModel.activateLoadingState()

        val data = getData()

        disposables.add(subscribeData(data,schedulerProvider))
    }



     override fun onError(e : Throwable) {
        stateModel.activateErrorState()
    }

     override fun onSuccess(data: List<CatFactId>) {
        _factsIds.value = (data)
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
        var shouldMockError = true
    }

    private val _fakeFactsIds = MutableLiveData<List<CatFactId>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override val factsIds: LiveData<List<CatFactId>>
        get() = _fakeFactsIds



    override fun refresh() {
        coroutineScope.launch {
            stateModel.activateLoadingState()
            delay(1000)
            stateModel.activateSuccessState()
        }
    }

    init {
        coroutineScope.launch {
            if(shouldMockError) {
                mockError()
            }else {
                stateModel.activateLoadingState()
                delay(1000)
                _fakeFactsIds.postValue(listOf(CatFactId(ID_1), CatFactId(ID_2)))
                stateModel.activateSuccessState()
            }

        }
    }

    private fun mockError() {
            stateModel.activateErrorState()
    }

}

abstract class CatFactsIdsViewModel constructor(
    val stateModel: StateModel
) : ViewModel() {

    abstract val factsIds: LiveData<List<CatFactId>>
    abstract fun refresh()



}
