package com.example.catapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun provideIoDispatcher() : CoroutineDispatcher
    fun provideDefaultDispatcher() : CoroutineDispatcher
    fun provideMainDispatcher() : CoroutineDispatcher
}