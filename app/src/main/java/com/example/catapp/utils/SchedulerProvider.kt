package com.example.catapp.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
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