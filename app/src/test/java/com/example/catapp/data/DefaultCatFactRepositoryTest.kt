package com.example.catapp.data

import com.example.catapp.data.repository.DefaultCatFactRepository
import com.example.catapp.network.CatFactsApi
import com.example.catapp.testUtils.CoroutineScopeRule
import com.example.catapp.testUtils.TestDispatchersProvider
import com.example.catapp.testUtils.Utils.TEST_CAT_FACT
import com.example.catapp.testUtils.Utils.TEST_ID
import com.example.catapp.testUtils.Utils.TEST_IDS
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultCatFactRepositoryTest {

    @MockK
    lateinit var catFactsApi: CatFactsApi

    lateinit var repository: DefaultCatFactRepository


    private val dispatchersProvider = TestDispatchersProvider()

    @get:Rule
    val coroutineTestRule = CoroutineScopeRule(dispatchersProvider.dispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = DefaultCatFactRepository(
            catFactsApi
        )
    }

    @Test
    fun `when catFactsApi returns Deferred(TEST_CAT_FACT) then getCatFact returns Deferred(TEST_CAT_FACT)`() {
        every { catFactsApi.getCatFact(TEST_ID) } returns CompletableDeferred(TEST_CAT_FACT)

        coroutineTestRule.runBlockingTest {
            val result = repository.getCatFactAsync(TEST_ID)

            val actual = result.await()
            assertThat(actual, `is`(TEST_CAT_FACT))
        }

    }

    @Test
    fun `when catFactsApi returns Deferred(TEST_IDS) then getFactsIds returns Deferred(TEST_IDS)`() {
        every { catFactsApi.getCatFacts() } returns CompletableDeferred(TEST_IDS)
        coroutineTestRule.runBlockingTest {
            val result = repository.getCatFactsIdsAsync()
            val actual = result.await()
            assertThat(actual, `is`(TEST_IDS))

        }

    }

}