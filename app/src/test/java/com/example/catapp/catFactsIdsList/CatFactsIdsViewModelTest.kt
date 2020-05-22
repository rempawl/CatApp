package com.example.catapp.catFactsIdsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.testUtils.CoroutineScopeRule
import com.example.catapp.testUtils.TestDispatchersProvider
import com.example.catapp.testUtils.Utils.TEST_IDS
import com.example.catapp.testUtils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatFactsIdsViewModelTest {


    private val dispatchersProvider = TestDispatchersProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScopeRule = CoroutineScopeRule(dispatchersProvider.dispatcher)

    @MockK
    lateinit var repository: CatFactRepository

    @MockK
    lateinit var errorModel: ErrorModel
    private lateinit var viewModel: DefaultCatFactsIdsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun `when repository  returns Error ,Then `() {
//        every { repository.getCatFactsIds() } returns



//        viewModel.fetchData()
//        TEST_SCHEDULER.advanceTimeBy(100, TimeUnit.MILLISECONDS)
//.
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `when repository returns TEST_IDS, items value is TEST_IDS`() {
        coEvery { repository.getCatFactsIdsAsync() } returns CompletableDeferred(TEST_IDS)
        viewModel = DefaultCatFactsIdsViewModel(repository, errorModel, dispatchersProvider)

        viewModel.fetchData()
        val result = viewModel.result.getOrAwaitValue ()

        assert(result is Result.Success)

        val data = (result as Result.Success).data as List<CatFactId>

        assertThat(data, `is`(TEST_IDS))
    }

    @Test
    fun `when fetching data is unsuccessful, Then  activateErrorState is called after activateloadingState`() {
//        every { repository.getCatFactsIdsRx() } returns Single.error(HttpException(RESPONSE_ERROR))

        viewModel = DefaultCatFactsIdsViewModel(repository, errorModel, dispatchersProvider)
        viewModel.fetchData()

        verify {
        }
    }

    @Test
    fun `when fetching data is successful, Then  activate  SuccessState is called after activateloadingState`() {
        viewModel = DefaultCatFactsIdsViewModel(repository, errorModel, dispatchersProvider)
        coroutineScopeRule.runBlockingTest {
            viewModel.fetchData()

            verify {
            }

        }
    }


    companion object

}
