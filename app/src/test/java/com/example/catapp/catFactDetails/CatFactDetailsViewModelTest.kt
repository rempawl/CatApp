package com.example.catapp.catFactDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.state.DefaultStateModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.formatDate
import com.example.catapp.getOrAwaitValue
import com.example.catapp.utils.SchedulerProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
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

@ExperimentalCoroutinesApi
class CatFactDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CatFactRepository

    @MockK
    lateinit var schedulerProvider: SchedulerProvider


    private lateinit var viewModel: DefaultCatFactDetailsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { schedulerProvider.getIOScheduler() } returns TEST_SCHEDULER
        every { schedulerProvider.getUIScheduler() } returns TEST_SCHEDULER

    }


    @Test
    fun `when CatRepository returns error ,Then isError is set to  true`() {

        every { repository.getCatFact(ID) } returns Single.error(HttpException(RESPONSE_ERROR))
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID,
            schedulerProvider = schedulerProvider,
            stateModel = DefaultStateModel()
        )



        val before = viewModel.stateModel.isError.getOrAwaitValue()
        assertFalse(before)

        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val after = viewModel.stateModel.isError.getOrAwaitValue()
        assertTrue(after)
    }

    @Test
    fun `when CatRepository returns TEST_CAT_FACT_MAPPED, then catFactDetails value is TEST_CAT_FACT_MAPPED`() {
        every { repository.getCatFact(ID) } returns Single.just(TEST_CAT_FACT_MAPPED)
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID,
            schedulerProvider = schedulerProvider,
            stateModel = DefaultStateModel()
        )


        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val data = viewModel.catFactDetail.getOrAwaitValue()
        assertThat(data, `is`(TEST_CAT_FACT_MAPPED))
    }

    @Test
    fun `when fetching data is unsuccessful, Then  activateErrorState is called after activateloadingState`() {
        every { repository.getCatFact(ID) } returns Single.error(HttpException(RESPONSE_ERROR))
        val spyModel = spyk(DefaultStateModel())
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            schedulerProvider = schedulerProvider,
            stateModel = spyModel,
            factId = ID

        )

        viewModel.fetchData()
        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        verify {
            spyModel.activateLoadingState()
            spyModel.activateErrorState()
        }
    }

    @Test
    fun `when fetching data is successful, Then  activate  SuccessState is called after activateloadingState`() {
        every { repository.getCatFact(ID) } returns Single.just(TEST_CAT_FACT)
        val spyModel = spyk(DefaultStateModel())


        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            schedulerProvider = schedulerProvider,
            stateModel = spyModel,
            factId = ID

        )

//        viewModel.fetchData()

        TEST_SCHEDULER.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        verify {
            spyModel.activateLoadingState()
            spyModel.activateSuccessState()
        }
    }



    companion object {
        private val RESPONSE_ERROR =
            Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))
        const val ID = "123"
        private val TEST_SCHEDULER = TestScheduler()

        val TEST_CAT_FACT = CatFact("test", "09-04-2020")

        val TEST_CAT_FACT_MAPPED = TEST_CAT_FACT.copy(updatedAt = TEST_CAT_FACT.formatDate())
    }

}