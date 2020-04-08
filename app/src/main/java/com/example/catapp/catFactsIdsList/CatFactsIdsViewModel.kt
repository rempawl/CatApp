package com.example.catapp.catFactsIdsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class CatFactViewModel  <T>  constructor(protected val catRepository: CatFactRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _items = MutableLiveData<T>()

    val items: LiveData<T>
        get() = _items

    private val _isNetworkError = MutableLiveData<Boolean>(false)
    val isNetworkError: LiveData<Boolean>
        get() = _isNetworkError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected abstract fun getData() : Single<T>

    fun refresh() {
        _items.value = null
        fetchData()
    }

    protected fun fetchData() {
        _isLoading.value = true

         val data = getData()

         subscribeData(data)

    }


    private fun subscribeData(data: Single<T>) {
        val d = data
        .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(
                {
                    onSuccess(it)
                },
                { e: Throwable ->
                    onError(e)
                }
            )
        disposables.add(d)
    }


    private fun onDataLoaded() {
     _isLoading.postValue(false)
        _isNetworkError.postValue(false)
        _errorMessage.postValue(null)

    }

    private fun onError(e: Throwable) {
        _isLoading.postValue(false)
        _isNetworkError.postValue(true)
        Log.d("kruci",e.message)
        _errorMessage.postValue(e.message)
    }



    private fun onSuccess(items: T) {
        _items.postValue(items)
        onDataLoaded()
    }

}


class CatFactsIdsViewModel  @Inject constructor(catFactRepository: CatFactRepository
) :  CatFactViewModel<List<CatFactId>>(catFactRepository) {


    override fun getData(): Single<List<CatFactId>> {
            return catRepository.getCatFactsIds()
    }

    init{

        fetchData()

    }

}
