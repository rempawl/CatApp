package com.example.catapp.catFactsIdsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.utils.CoroutineScopeRule
import com.example.catapp.fakes.TestDispatcherProvider
import com.example.catapp.fakes.Utils.ERROR_TEXT
import com.example.catapp.fakes.Utils.RESPONSE_ERROR_404
import com.example.catapp.fakes.Utils.TEST_IDS
import com.example.catapp.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatFactsIdsViewModelTest {


    private val dispatchersProvider =
        TestDispatcherProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScopeRule =
        CoroutineScopeRule(dispatchersProvider.dispatcher)

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
    fun `when repository  returns Error_404 ,Then result is Error and message is ERROR_TEXT `() {
//        coEvery { repository.getCatFactsIds() } returns
        every { errorModel.getErrorMessage(RESPONSE_ERROR_404) } returns ERROR_TEXT
        viewModel = DefaultCatFactsIdsViewModel(repository,errorModel,dispatchersProvider)

        coroutineScopeRule.runBlockingTest {

            viewModel.fetchData()
            val result = viewModel.result.getOrAwaitValue()

            assertTrue(result is Result.Error)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `when repository returns TEST_IDS, result is Success and value is TEST_IDS`() {
        coEvery { repository.getCatFactsIdsAsync() } returns CompletableDeferred(TEST_IDS)

        viewModel = DefaultCatFactsIdsViewModel(repository, errorModel, dispatchersProvider)

        coroutineScopeRule.runBlockingTest {
            viewModel.fetchData()
            val result = viewModel.result.getOrAwaitValue()

            assertTrue( result is Result.Success)
            val data = (result as Result.Success).data as List<CatFactId>

            assertEquals(TEST_IDS,data)
        }
    }




    companion object

}
