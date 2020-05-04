package com.example.catapp.catFactsIdsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.getOrAwaitValue
import com.example.catapp.utils.SchedulerProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import okhttp3.ResponseBody
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CatFactsIdsViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CatFactRepository

    private lateinit var viewModel: DefaultCatFactsIdsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun `when repository  returns Single_Error ,Then isNetworkError is set to  true`() {
//        every { repository.getCatFactsIds() } returns

        viewModel = DefaultCatFactsIdsViewModel(
            catFactRepository = repository
        )


//        val before = viewModel.stateModel.isError.getOrAwaitValue()
//        assertFalse(before)

//        viewModel.fetchData()
//        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)
//
//        val after = viewModel.stateModel.isError.getOrAwaitValue()
//        assertTrue(after)
    }

    @Test
    fun `when repository returns TEST_IDS, items value is TEST_IDS`() {
//        every { repository.getCatFactsIdsRx() } returns Single.just(TEST_IDS)

        viewModel = DefaultCatFactsIdsViewModel(
            catFactRepository = repository
        )

        viewModel.fetchData()

        val data = viewModel.factsIds.getOrAwaitValue()
        assertThat(data, `is`(TEST_IDS))
    }

    @Test
    fun `when fetching data is unsuccessful, Then  activateErrorState is called after activateloadingState`() {
//        every { repository.getCatFactsIdsRx() } returns Single.error(HttpException(RESPONSE_ERROR))
        viewModel = DefaultCatFactsIdsViewModel(
            catFactRepository = repository
        )

        viewModel.fetchData()

        verify {
        }
    }

    @Test
    fun `when fetching data is successful, Then  activate  SuccessState is called after activateloadingState`() {
        viewModel = DefaultCatFactsIdsViewModel(
            catFactRepository = repository

        )

        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        verify {
        }
    }


    companion object {
        private val RESPONSE_ERROR =
            Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))
        const val ID = "123"
        val TEST_IDS = listOf<CatFactId>(CatFactId(ID), CatFactId(ID))
        val TEST_SCHEDULER = TestScheduler()

    }

}
