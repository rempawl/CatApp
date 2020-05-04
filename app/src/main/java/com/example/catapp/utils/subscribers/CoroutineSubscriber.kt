package com.example.catapp.utils.subscribers

import kotlinx.coroutines.Deferred

interface CoroutineSubscriber<T> {

    suspend fun subscribeData(result: Deferred<T>) {
        val data = result.await()
        try {
            onSuccess(data)
        } catch (e: Throwable) {
            onError(e)
        }
    }

    fun onSuccess(result: T)

    fun onError(e: Throwable)
}