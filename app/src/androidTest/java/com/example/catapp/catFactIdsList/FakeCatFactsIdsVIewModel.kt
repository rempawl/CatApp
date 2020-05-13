package com.example.catapp.catFactIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.state.StateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                _fakeFactsIds.postValue(listOf(
                    CatFactId(ID_1),
                    CatFactId(
                        ID_2
                    )
                ))
                stateModel.activateSuccessState()
            }

        }
    }

    private fun mockError() {
        stateModel.activateErrorState()
    }

}
