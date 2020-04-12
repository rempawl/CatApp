package com.example.catapp.catFactDetails

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.catapp.DefaultStateModel
import com.example.catapp.R
import com.example.catapp.catFactsIdsList.FakeFactsIdsViewModel
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test

class CatFactDetailsFragmentTest {

    lateinit var viewModel: CatFactDetailsViewModel

    lateinit var fragmentScenario: FragmentScenario<TestCatFactDetailsFragment>



    @Test
    fun whenErrorOccursWhileFetchingData_ThenErrorViewIsDisplayed() {
        FakeFactsIdsViewModel.SHOULD_MOCK_ERROR = true

        viewModel = FakeCatFactDetailsViewModel(DefaultStateModel())
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactDetailsFragment>(Bundle(), R.style.AppTheme)

        viewModel.init()

        Espresso
            .onView(withId(R.id.error_view))
            .check(matches(isDisplayed()))

    }

    @Test
    fun whenDataIsSuccessfullyFetched_ThenFactContainerIsDisplayed() {
        FakeCatFactDetailsViewModel.SHOULD_MOCK_ERROR = false
        viewModel = FakeCatFactDetailsViewModel(DefaultStateModel())
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario = launchFragmentInContainer(Bundle(), R.style.AppTheme)

        viewModel.init()

        Espresso
            .onView(withId(R.id.fact_container))
            .check(matches(isDisplayed()))

    }


}