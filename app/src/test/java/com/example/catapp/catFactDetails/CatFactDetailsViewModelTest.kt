package com.example.catapp.catFactDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.fakes.TestDispatcherProvider
import com.example.catapp.fakes.Utils.ERROR_TEXT
import com.example.catapp.fakes.Utils.RESPONSE_ERROR_404
import com.example.catapp.fakes.Utils.TEST_CAT_FACT
import com.example.catapp.fakes.Utils.TEST_CAT_FACT_MAPPED
import com.example.catapp.fakes.Utils.TEST_ID_1
import com.example.catapp.utils.CoroutineScopeRule
import com.example.catapp.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatFactDetailsViewModelTest {

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

    private lateinit var viewModel: DefaultCatFactDetailsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun `when CatRepository returns error ,then result is Error adn message is ERROR_TEXT`() {
        //todo

        every { errorModel.getErrorMessage(RESPONSE_ERROR_404) } returns ERROR_TEXT

        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID_1, errorModel, dispatchersProvider)

        coroutineScopeRule.runBlockingTest {
            viewModel.fetchData()

            val result = viewModel.result.getOrAwaitValue()
            assertTrue(result is Result.Error)

            val actual = (result as Result.Error).message
            assertThat(actual, `is`(ERROR_TEXT))
        }

    }

    @Test
    fun `when CatRepository returns TEST_CAT_FACT, then result  is success and value is TEST_CAT_FACT_MAPPED `() {
        coEvery { repository.getCatFactAsync(TEST_ID_1) } returns CompletableDeferred(TEST_CAT_FACT)

        viewModel =
            DefaultCatFactDetailsViewModel(repository, TEST_ID_1, errorModel, dispatchersProvider)
        coroutineScopeRule.runBlockingTest {

            viewModel.fetchData()

            val data = viewModel.result.getOrAwaitValue()
            assertTrue(data is Result.Success)

            val catFact = (data as Result.Success).data as CatFact

            assertThat(catFact, `is`(TEST_CAT_FACT_MAPPED))
        }
    }


    companion object

}