package com.example.catapp.catFactDetails

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.state.DefaultStateModel
import com.example.catapp.R
import com.example.catapp.catFactsIdsList.FakeFactsIdsViewModel
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatFactDetailsFragmentTest {

    private lateinit var viewModel: FakeCatFactDetailsViewModel

    private lateinit var fragmentScenario: FragmentScenario<TestCatFactDetailsFragment>



    @Test
    fun whenErrorOccursWhileFetchingData_ThenErrorViewIsDisplayed() {

        FakeFactsIdsViewModel.shouldMockError = true

        viewModel = FakeCatFactDetailsViewModel(DefaultStateModel())
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactDetailsFragment>(Bundle(), R.style.AppTheme)


        Espresso
            .onView(withText(R.string.error_msg))
            .check(matches(isDisplayed()))

    }

    @Test
    fun whenDataIsSuccessfullyFetched_ThenFactContainerIsDisplayed() {
        FakeCatFactDetailsViewModel.shouldMockError = false
        viewModel = FakeCatFactDetailsViewModel(DefaultStateModel())
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario = launchFragmentInContainer<TestCatFactDetailsFragment>(Bundle(), R.style.AppTheme)


        Espresso
            .onView(withId(R.id.fact_container))
            .check(matches(isDisplayed()))

    }


}