package com.example.catapp.utils.subscribers

import com.example.catapp.utils.providers.SchedulerProvider
import io.reactivex.disposables.Disposable

interface Subscriber<T> {
    fun subscribeData(data: T, schedulerProvider: SchedulerProvider): Disposable
}

