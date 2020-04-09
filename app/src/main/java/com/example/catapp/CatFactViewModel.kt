package com.example.catapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okio.Timeout
import retrofit2.HttpException
import java.io.IOException

@Suppress("UnstableApiUsage")
abstract class CatFactViewModel<T> constructor(
    protected val catRepository: CatFactRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _items = MutableLiveData<T>()
    val items: LiveData<T>
        get() = _items

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    val wasInitialLoadPerformed : LiveData<Boolean>
     get() = _wasInitialLoadPerformed

    private val _isNetworkError = MutableLiveData(false)
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

    protected abstract fun getData(): Single<T>

    fun refresh() {
        _items.value = null
        fetchData()
    }

    fun init(){
        _wasInitialLoadPerformed.value = true
        fetchData()
    }
    private fun fetchData() {
        _isLoading.value = true

        val data = getData()

        subscribeData(data)

    }


    private fun subscribeData(data: Single<T>) {
        val d = data
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { _isLoading.postValue(false) }
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
        _isNetworkError.postValue(false)
        _errorMessage.postValue(null)
    }

    private fun setError(message: String?) {
        _isNetworkError.postValue(true)
        _errorMessage.postValue(message)
    }

    private fun onError(e: Throwable) {
        Log.d("kruci",e.message.toString())
        when (e) {
            is HttpException -> setError(e.message)
            is IOException -> setError(e.message)
            else -> setError(null)
        }
    }

    private fun onSuccess(items: T) {

        _items.postValue(items)
        onDataLoaded()
    }

}
