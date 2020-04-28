package com.example.catapp.utils

import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface Subscriber<T> {
    fun subscribeData(data: T, schedulerProvider: SchedulerProvider): Disposable
}

interface SingleSubscriber<R> : Subscriber<Single<R>> {

    override fun subscribeData(data: Single<R>, schedulerProvider: SchedulerProvider): Disposable {
        return data
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
            .subscribe(
                { onSuccess(it) },
                { e -> onError(e) }
            )
    }

    fun onSuccess(data: R)

    fun onError(e: Throwable)
}
