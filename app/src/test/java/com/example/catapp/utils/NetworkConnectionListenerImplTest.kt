package com.example.catapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.testUtils.getOrAwaitValue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NetworkConnectionListenerImplTest{
    lateinit var networkConnectionListenerImpl: NetworkConnectionListenerImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        networkConnectionListenerImpl = NetworkConnectionListenerImpl()
    }

    @Test
    fun `when onActive is called Then isConnectionActive value is true`(){
        networkConnectionListenerImpl.onActive()
        val isActive = networkConnectionListenerImpl.isConnectionActive.getOrAwaitValue()

        assertTrue(isActive)
    }

    @Test
    fun `when onInactive is called then isConnectionActive value is false`(){
        networkConnectionListenerImpl.onInactive()

        val isActive = networkConnectionListenerImpl.isConnectionActive.getOrAwaitValue()

        assertFalse(isActive)
    }
}