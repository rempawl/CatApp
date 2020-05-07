package com.example.catapp.catFactsIdsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.State
import com.example.catapp.utils.subscribers.CoroutineSubscriber
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultCatFactsIdsViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val errorModel: ErrorModel

) : CatFactsIdsViewModel(),
    CoroutineSubscriber<List<CatFactId>> {

    private val _factsIds = MutableLiveData<List<CatFactId>>()

    private val _state = MutableLiveData<State<*>>()
    override val state: LiveData<State<*>>
        get() = _state

    override val factsIds: LiveData<List<CatFactId>>
        get() = _factsIds


    override fun refresh() {
        _factsIds.value = null
        fetchData()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _state.value = (State.Loading)

        val data = catFactRepository.getCatFactsIdsAsync()
        viewModelScope.launch {
            subscribeData(data)
        }

    }

    override fun onError(e: Throwable) {
        val message = errorModel.getErrorMessage(e)
        _state.value = (State.Error(message))
    }

    override fun onSuccess(result: List<CatFactId>) {
        _factsIds.value = (result)
        _state.value = (State.Success(result))
    }


}
