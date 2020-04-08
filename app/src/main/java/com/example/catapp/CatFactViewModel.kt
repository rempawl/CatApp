package com.example.catapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

abstract class ErrorModel {
    sealed class ErrorState{
        object Active : ErrorState()
        object NotActive :ErrorState()
    }

    private var currentErrorState : ErrorState = ErrorState.NotActive

    private val _isErrorActive = MutableLiveData(false)
    val isErrorActive: LiveData<Boolean>
        get() = _isErrorActive


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage


    fun unsetError() {
        if(currentErrorState is ErrorState.Active) {
            currentErrorState = ErrorState.NotActive
            _isErrorActive.postValue(false)
            _errorMessage.postValue(null)
        }
    }

    fun setError(message: String?) {
        _isErrorActive.postValue(true)
        _errorMessage.postValue(message)
        currentErrorState = ErrorState.Active
    }

}

class DefaultErrorModel @Inject constructor() : ErrorModel()

@Suppress("UnstableApiUsage")
abstract class CatFactViewModel<T> constructor(
    protected val catRepository: CatFactRepository,
    val errorModel: ErrorModel
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _items = MutableLiveData<T>()
    val items: LiveData<T>
        get() = _items

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed


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

    fun init() {
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


    private fun onError(e: Throwable) {
        when (e) {
            is HttpException -> errorModel.setError(e.message)
            is IOException -> errorModel.setError(e.message)
            else -> errorModel.setError(null)
        }
    }

    private fun onSuccess(items: T) {
        _items.postValue(items)
        errorModel.unsetError()
    }

}
