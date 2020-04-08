package com.example.catapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.catFactDetails.CatFactDetailsViewModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

internal class CatFactDetailsViewModelTest{

    @get:Rule
    val instantTaskExecutorRule  = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CatFactRepository

    lateinit var viewModel: CatFactDetailsViewModel

    @BeforeEach fun setUp(){
        MockKAnnotations.init(this)
        viewModel = CatFactDetailsViewModel(catFactRepository = repository,factId = "123")
    }

    @Nested
    inner class GetData{

        @Test
        fun `when getData throws exception`(){

            every { repository.getCatFact("123") } throws HttpException(RESPONSE_ERROR)


        }

    }
    companion object{
        private val RESPONSE_ERROR  = Response.error<CatFact>(404, ResponseBody.create(null,"404 File not Found"))

    }

}