package com.example.catapp.utils.subscribers

import com.example.catapp.utils.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface SingleSubscriber<R> :    Subscriber<Single<R>> {

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


