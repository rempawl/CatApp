package com.example.catapp.utils.subscribers

import kotlinx.coroutines.Deferred

@Suppress("SpellCheckingInspection")
interface CoroutineSubscriber<T> {

    suspend fun subscribeData(result: Deferred<T>) {
        try {
            val data = result.await()
            onSuccess(data)
        } catch (e: Throwable) {
            onError(e)
        }
    }

    fun onSuccess(result: T)

    fun onError(e: Throwable)
}