package com.example.catapp.utils.providers

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun getIOScheduler(): Scheduler
    fun getUIScheduler(): Scheduler
}

