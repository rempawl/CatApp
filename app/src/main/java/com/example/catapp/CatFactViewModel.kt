package com.example.catapp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatFactRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun getIOScheduler(): Scheduler
    fun getUIScheduler(): Scheduler
}

class DefaultSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun getIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun getUIScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

@Suppress("UnstableApiUsage")
abstract class CatFactViewModel<T> constructor(
    protected val catRepository: CatFactRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private var currentErrorState: ErrorState = ErrorState.Inactive

    private val disposables = CompositeDisposable()

    private val _items = MutableLiveData<T>()
    val items: LiveData<T>
        get() = _items

    private val _wasInitialLoadPerformed = MutableLiveData(false)
    val wasInitialLoadPerformed: LiveData<Boolean>
        get() = _wasInitialLoadPerformed

    private val _isNetworkError = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean>
        get() = _isNetworkError


    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    abstract fun getData(): Single<T>

    fun refresh() {
        _items.value = null
        fetchData()
    }

    fun init() {
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


    private fun subscribeData(data: Single<T>, ioScheduler: Scheduler, uiScheduler: Scheduler) {
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
        if(currentErrorState is ErrorState.Inactive) {
            _isNetworkError.postValue(true)
            currentErrorState = ErrorState.Active
        }

    }

    private fun onSuccess(items: T) {
        _items.postValue(items)
        if (currentErrorState is ErrorState.Active) {
            _isNetworkError.postValue(false)
            currentErrorState = ErrorState.Inactive
        }

    }

}

sealed class ErrorState {
    object Active : ErrorState()
    object Inactive : ErrorState()
}