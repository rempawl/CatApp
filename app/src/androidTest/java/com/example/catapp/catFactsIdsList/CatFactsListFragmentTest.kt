package com.example.catapp.catFactsIdsList

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.data.CatFactId
import com.example.catapp.databinding.CatFactsListFragmentBinding
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatFactsListFragmentTest {


    @MockK
    lateinit var viewModel: CatFactsIdsViewModel

    lateinit var adapter: CatFactsListAdapter

    @MockK
    lateinit var navController: NavController

    lateinit var fragmentScenario: FragmentScenario<TestCatFactsIdsListFragment>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        adapter = CatFactsListAdapter()

        TestCatFactsIdsListFragment.testAdapter = adapter
        TestCatFactsIdsListFragment.testViewModel = viewModel

        every { viewModel.factsIds } returns MutableLiveData(TEST_IDS)


        fragmentScenario =
            launchFragmentInContainer<TestCatFactsIdsListFragment>(Bundle(), R.style.AppTheme)

    }

    @Test
    fun whenListItemIsClicked_ThenNavigateToCatFactDetails() {
//         { viewModel.wasInitialLoadPerformed } returns MutableLiveData(false)
        every { viewModel.isLoading } returns MutableLiveData(false)


        fragmentScenario.onFragment {fragment ->
            Navigation.setViewNavController(fragment.view!!, navController)
        }


        Espresso.onView(withId(R.id.cat_facts_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(ID)), click()
                )
            )

        verify { navController.navigate(CatFactsListFragmentDirections
            .navigationCatListFragmentToNavigationCatFactDetails(ID)) }
    }



    companion object {
        const val ID = "123"
        val TEST_IDS = listOf(CatFactId(ID), CatFactId("345"))
    }

}