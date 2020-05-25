package com.example.catapp.catFactIdsList

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.R
import com.example.catapp.catFactIdsList.FakeFactsIdsViewModel.Companion.shouldMockError
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.catFactsIdsList.CatFactsListAdapter
import com.example.catapp.fakes.FakeDispatcherProvider
import com.example.catapp.utils.providers.DispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CatFactsIdsFragmentTest {

    @MockK
    lateinit var navController: NavController

    private val dispatcherProvider: DispatcherProvider = FakeDispatcherProvider()
    private lateinit var viewModel: CatFactsIdsViewModel
    private lateinit var fragmentScenario: FragmentScenario<TestCatFactsIdsIdsFragment>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        TestCatFactsIdsIdsFragment.testAdapter =
            CatFactsListAdapter {}

    }

/*
todo
    @Test
    fun whenListItemIsClicked_ThenNavigateToCatFactDetails() {


        val context = ApplicationProvider.getApplicationContext<MyApp>()
        val text = context.getString(R.string.cat_fact_id,ID_1)

        Espresso.onView(withId(R.id.cat_facts_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(text)),click()
                )
            )

        verify {
            navController.navigate(
                CatFactsListFragmentDirections
                    .navigationCatListFragmentToNavigationCatFactDetails(ID_1)
            )
        }
    }
*/

    @Test
    fun whenErrorOccursWhileFetchingData_ThenErrorViewIsDisplayed() {

        shouldMockError = true
        viewModel = FakeFactsIdsViewModel(dispatcherProvider)
        TestCatFactsIdsIdsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsIdsFragment>(Bundle(), R.style.AppTheme)

        Espresso
            .onView(withText(R.string.error_msg))
            .check(matches(isDisplayed()))


    }

    @Test
    fun whenRefreshBtnIsCLicked_ThenRefreshIsCalledAndLoadingLayoutIsDisplayed() {
        shouldMockError = false
        viewModel = spyk(FakeFactsIdsViewModel(dispatcherProvider))
        TestCatFactsIdsIdsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsIdsFragment>(Bundle(), R.style.AppTheme)

        Espresso.onView(withId(R.id.refresh_btn)).perform(click())
        Espresso.onView(withId(R.id.loading)).check(matches(isDisplayed()))

        verify { viewModel.fetchData() }


    }

    companion object

}