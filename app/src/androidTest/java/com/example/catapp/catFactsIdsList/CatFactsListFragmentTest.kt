package com.example.catapp.catFactsIdsList

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.state.DefaultStateModel
import com.example.catapp.R
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatFactsListFragmentTest {

    @MockK
    lateinit var navController: NavController

    private lateinit var viewModel: CatFactsIdsViewModel
    private lateinit var fragmentScenario: FragmentScenario<TestCatFactsIdsListFragment>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        TestCatFactsIdsListFragment.testAdapter = CatFactsListAdapter()

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

        FakeFactsIdsViewModel.SHOULD_MOCK_ERROR = true
        viewModel = FakeFactsIdsViewModel(DefaultStateModel())
        TestCatFactsIdsListFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsListFragment>(Bundle(), R.style.AppTheme)

        viewModel.init()

        Espresso.onView(withId(R.id.error_view)).check(matches(isDisplayed()))


    }

    @Test
    fun whenRefreshBtnIsCLicked_ThenRefreshIsCalledAndLoadingIconIsDisplayed() {

        viewModel = spyk(FakeFactsIdsViewModel(DefaultStateModel()))
        TestCatFactsIdsListFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsListFragment>(Bundle(), R.style.AppTheme)

        Espresso.onView(withId(R.id.refresh_btn)).perform(click())
        Espresso.onView(withId(R.id.loading_icon)).check(matches(isDisplayed()))

        verify { viewModel.refresh() }


    }

    companion object

}