package com.example.catapp.catFactDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.data.entities.formatUpdateDate
import com.example.catapp.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.schedulers.TestScheduler
import okhttp3.ResponseBody
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class CatFactDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CatFactRepository


    private lateinit var viewModel: DefaultCatFactDetailsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }


    @Test
    fun `when CatRepository returns error ,Then isError is set to  true`() {

//        every { repository.getCatFactRx(ID) } returns Single.error(HttpException(RESPONSE_ERROR))
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID
        )


    }

    @Test
    fun `when CatRepository returns TEST_CAT_FACT_MAPPED, then catFactDetails value is TEST_CAT_FACT_MAPPED`() {
//        every { repository.getCatFactRx(ID) } returns Single.just(TEST_CAT_FACT_MAPPED)
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID
        )


        viewModel.fetchData()

        val data = viewModel.catFactDetail.getOrAwaitValue()
        assertThat(data, `is`(TEST_CAT_FACT_MAPPED))
    }

    @Test
    fun `when fetching data is unsuccessful, Then  activateErrorState is called after activateloadingState`() {
//        every { repository.getCatFactRx(ID) } returns Single.error(HttpException(RESPONSE_ERROR))
//        val spyModel = spyk(DefaultStateModel())
        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID
        )

        viewModel.fetchData()

        verify {
        }
    }

    @Test
    fun `when fetching data is successful, Then  activate  SuccessState is called after activateloadingState`() {
//        every { repository.getCatFactRx(ID) } returns Single.just(TEST_CAT_FACT)


        viewModel = DefaultCatFactDetailsViewModel(
            catFactRepository = repository,
            factId = ID

        )

        verify {
        }
    }


    companion object {
        private val RESPONSE_ERROR =
            Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))
        const val ID = "123"
        private val TEST_SCHEDULER = TestScheduler()

        val TEST_CAT_FACT =
            CatFact("test", "09-04-2020")

        val TEST_CAT_FACT_MAPPED = TEST_CAT_FACT.copy(updatedAt = TEST_CAT_FACT.formatUpdateDate())
    }

}