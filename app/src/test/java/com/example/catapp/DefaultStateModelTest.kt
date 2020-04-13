package com.example.catapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.state.DefaultStateModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultStateModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var model : DefaultStateModel

    @Before
    fun setUp(){
        model = DefaultStateModel()
    }

    @Test
    fun `when activateErrorState is called, Then isError is true, isLoading is false, isSuccess is false`(){
        model.activateErrorState()
        val error = model.isError.getOrAwaitValue()
        assertTrue(error)

        val success = model.isSuccess.getOrAwaitValue()
        assertFalse(success)

        val loading = model.isLoading.getOrAwaitValue()
        assertFalse(loading)

    }

    @Test
    fun `when  activateLoadingState is called, Then isLoading is true, isError is false, isSuccess is false`(){

            model.activateLoadingState()

            val error = model.isError.getOrAwaitValue()
            assertFalse(error)

            val success = model.isSuccess.getOrAwaitValue()
            assertFalse(success)

            val loading = model.isLoading.getOrAwaitValue()
            assertTrue(loading)


    }

    @Test
    fun `when  activateSuccessState is called, Then isSuccess is true, isError is false, isLoading is false`(){

        model.activateSuccessState()

        val error = model.isError.getOrAwaitValue()
        assertFalse(error)

        val success = model.isSuccess.getOrAwaitValue()
        assertTrue(success)

        val loading = model.isLoading.getOrAwaitValue()
        assertFalse(loading)


    }

}