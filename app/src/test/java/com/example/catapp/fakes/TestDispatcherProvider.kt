package com.example.catapp.fakes

import com.example.catapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {
    val dispatcher = TestCoroutineDispatcher()
    override fun provideIoDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun provideDefaultDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun provideMainDispatcher(): TestCoroutineDispatcher = dispatcher
}