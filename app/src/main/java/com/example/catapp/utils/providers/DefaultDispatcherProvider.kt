package com.example.catapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDispatcherProvider  @Inject constructor(): DispatcherProvider{
    override fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    override fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    override fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}