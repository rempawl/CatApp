package com.example.catapp.utils.providers

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultDispatcherProviderTest {


    lateinit var dispatcherProvider: DefaultDispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = DefaultDispatcherProvider()

    }

    @Test
    fun provideIoDispatcher() {

        val actual = dispatcherProvider.provideIoDispatcher()
        assertEquals(actual, Dispatchers.IO)

    }

    @Test
    fun provideDefaultDispatcher() {
        val actual = dispatcherProvider.provideDefaultDispatcher()
        assertEquals(actual ,Dispatchers.Default)

//        assertThat(actual, `is`(Dispatchers.Default))
    }

    @Test
    fun provideMainDispatcher() {
        val actual = dispatcherProvider.provideMainDispatcher()

        assertEquals(actual,Dispatchers.Main)
//        assertThat(actual, `is`(Dispatchers.Main))
    }
}