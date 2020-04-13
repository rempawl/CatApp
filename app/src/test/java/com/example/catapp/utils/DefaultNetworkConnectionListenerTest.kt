package com.example.catapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultNetworkConnectionListenerTest{
    lateinit var networkConnectionListener: DefaultNetworkConnectionListener

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        networkConnectionListener = DefaultNetworkConnectionListener()
    }

    @Test
    fun `when onActive is called Then isConnectionActive value is true`(){
        networkConnectionListener.onActive()
        val isActive = networkConnectionListener.isConnectionActive.getOrAwaitValue()

        assertTrue(isActive)
    }

    @Test
    fun `when onInactive is called then isConnectionActive value is false`(){
        networkConnectionListener.onInactive()

        val isActive = networkConnectionListener.isConnectionActive.getOrAwaitValue()

        assertFalse(isActive)
    }
}