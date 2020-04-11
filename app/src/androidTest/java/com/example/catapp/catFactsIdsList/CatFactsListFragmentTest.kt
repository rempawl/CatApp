package com.example.catapp.catFactsIdsList

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.DefaultStateModel
import com.example.catapp.MyApp
import com.example.catapp.R
import com.example.catapp.catFactsIdsList.FakeFactsIdsViewModel.Companion.ID_1
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
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

    lateinit var viewModel: CatFactsIdsViewModel
    private lateinit var fragmentScenario: FragmentScenario<TestCatFactsIdsListFragment>
//    lateinit var viewModel: CatFactsIdsViewModel

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
    fun whenRefreshBtnIsCLicked_ThenRefreshIsCalled(){

        viewModel = spyk(FakeFactsIdsViewModel(DefaultStateModel()))
        TestCatFactsIdsListFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsListFragment>(Bundle(), R.style.AppTheme)

        Espresso.onView(withId(R.id.refresh_btn)).perform(click())

        verify{ viewModel.refresh()}


    }

    companion object

}