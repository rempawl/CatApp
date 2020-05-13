package com.example.catapp.catFactDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.testUtils.CoroutineScopeRule
import com.example.catapp.testUtils.TestDispatchersProvider
import com.example.catapp.testUtils.Utils.TEST_CAT_FACT_MAPPED
import com.example.catapp.testUtils.Utils.TEST_ID
import com.example.catapp.testUtils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatFactDetailsViewModelTest {

    private val dispatchersProvider = TestDispatchersProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScopeRule = CoroutineScopeRule(dispatchersProvider.dispatcher)

    @MockK
    lateinit var repository: CatFactRepository

    @MockK
    lateinit var errorModel: ErrorModel

    private lateinit var viewModel: DefaultCatFactDetailsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }


    @Test
    fun `when CatRepository returns error ,Then isError is set to  true`() {

//        every { repository.getCatFactAsync(TEST_ID) } returns
        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID, errorModel, dispatchersProvider)


    }

    @Test
    fun `when CatRepository returns TEST_CAT_FACT_MAPPED, then catFactDetails value is TEST_CAT_FACT_MAPPED`() {
//        every { repository.getCatFactRx(ID) } returns Single.just(TEST_CAT_FACT_MAPPED)
        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID, errorModel, dispatchersProvider)

        viewModel.fetchData()

        val data = viewModel.catFactDetail.getOrAwaitValue()
        assertThat(data, `is`(TEST_CAT_FACT_MAPPED))
    }

    @Test
    fun `when fetching data is unsuccessful, Then  activateErrorState is called after activateloadingState`() {
//        every { repository.getCatFactRx(ID) } returns Single.error(HttpException(RESPONSE_ERROR))
//        val spyModel = spyk(DefaultStateModel())
        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID, errorModel, dispatchersProvider)

        viewModel.fetchData()

        verify {
        }
    }

    @Test
    fun `when fetching data is successful, Then  activate  SuccessState is called after activateloadingState`() {
//        every { repository.getCatFactRx(ID) } returns Single.just(TEST_CAT_FACT)
        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID, errorModel, dispatchersProvider)

        verify {
        }
    }


    companion object

}