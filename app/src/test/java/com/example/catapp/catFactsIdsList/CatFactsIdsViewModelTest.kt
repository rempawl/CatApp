package com.example.catapp.catFactsIdsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.DefaultStateModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import com.example.catapp.getOrAwaitValue
import com.example.catapp.utils.SchedulerProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CatFactsIdsViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CatFactRepository


    @MockK
    lateinit var schedulerProvider: SchedulerProvider

    private lateinit var viewModel: DefaultCatFactsIdsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { schedulerProvider.getIOScheduler() } returns TEST_SCHEDULER
        every { schedulerProvider.getUIScheduler() } returns TEST_SCHEDULER

        viewModel = DefaultCatFactsIdsViewModel(
            catFactRepository = repository,
            schedulerProvider = schedulerProvider,
            stateModel = DefaultStateModel()

        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `When  init is called _wasInitialLoadPerformed is set to true and data is fetched`() {
        every { repository.getCatFactsIds() } returns Single.just(TEST_IDS)

        val before = viewModel.wasInitialLoadPerformed.getOrAwaitValue()
        Assertions.assertFalse(before)

        viewModel.init()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val after = viewModel.wasInitialLoadPerformed.getOrAwaitValue()
        Assertions.assertTrue(after)
        val data = viewModel.factsIds.getOrAwaitValue()
        assertThat(data, `is`(TEST_IDS))


    }

    @Test
    fun `when CatFactApi  returns Single_Error ,Then isNetworkError is set to  true`() {

        every { repository.getCatFactsIds() } returns Single.error(HttpException(RESPONSE_ERROR))


        val before = viewModel.stateModel.isError.getOrAwaitValue()
        assertFalse(before)

        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val after = viewModel.stateModel.isError.getOrAwaitValue()
        assertTrue(after)
    }

    @Test
    fun `when CatFactApi returns TEST_CAT_FACT, then items value is TEST_CAT_FACT`() {
        every { repository.getCatFactsIds() } returns Single.just(TEST_IDS)



        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val data = viewModel.factsIds.getOrAwaitValue()
        assertThat(data, `is`(TEST_IDS))
    }

    //todo spy loading


    companion object {
        private val RESPONSE_ERROR =
            Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))
        const val ID = "123"
        val TEST_IDS = listOf<CatFactId>(CatFactId(ID), CatFactId(ID))
        val TEST_SCHEDULER = TestScheduler()

    }

}
