package com.example.catapp.catFactsIdsList

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.DefaultCatRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CatFactsListViewModel @Inject constructor(private val catRepository: DefaultCatRepository) :
    ViewModel() {
    val disposables = CompositeDisposable()


    //todo hide list onRefresh

    private val _factsIdsList = MutableLiveData<List<CatFactId>>()
    val factsIdsList: LiveData<List<CatFactId>>
        get() = _factsIdsList

    private val _isNetworkError = MutableLiveData<Boolean>(false)
    val isNetworkError: LiveData<Boolean>
        get() = _isNetworkError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
    get() = _errorMessage

     val isLoading = ObservableField<Boolean>(true)
    init {
        fetchCatFactsIds()
    }

    private fun fetchCatFactsIds() {
        isLoading.set(true)
        val d = catRepository.getCatFactsIds()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(
                { Log.d("kruci","freshh")
                    onSuccess(it) },
                { e: Throwable ->
                    setError(e)
                }
            )
        disposables.add(d)


    }

    private fun onSuccess(factsIds: List<CatFactId>?) {
        isLoading.set(false)
        _factsIdsList.postValue(factsIds)
        _isNetworkError.postValue(false)
        _errorMessage.postValue(null)
    }

    private fun setError(e: Throwable) {
        _isNetworkError.postValue( true)
        _errorMessage.postValue( e.message)
    }

    fun refresh() {
        fetchCatFactsIds()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


}
