package com.example.catapp.catFactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.data.CatFact
import com.example.catapp.state.StateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FakeCatFactDetailsViewModel constructor(stateModel: StateModel) :
    CatFactDetailsViewModel(stateModel) {

    companion object {
        val FAKE_FACT = CatFact("TEXT", "DATE")
        var shouldMockError = true

    }

    private val _catFactDetail = MutableLiveData<CatFact>()
    override val catFactDetail: LiveData<CatFact>
        get() = _catFactDetail

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun refresh() {

    }

    init {
        coroutineScope.launch {

            if (shouldMockError) {
                mockError()
            } else {
                stateModel.activateLoadingState()
                delay(1000)
                _catFactDetail.value = FAKE_FACT
                stateModel.activateSuccessState()
            }
        }
    }

    private fun mockError() {
        stateModel.activateErrorState()
    }

}
