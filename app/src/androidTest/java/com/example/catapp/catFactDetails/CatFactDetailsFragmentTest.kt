package com.example.catapp.catFactDetails

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CatFactDetailsFragmentTest {

    private lateinit var viewModel: CatFactDetailsViewModel

    private lateinit var fragmentScenario: FragmentScenario<TestCatFactDetailsFragment>


//    private val dispatchersProvider = TestDispatcherProvider() todo


    @Test
    fun whenErrorOccursWhileFetchingData_ThenErrorViewIsDisplayed() {

        FakeCatFactDetailsViewModel.shouldMockError = true

//        viewModel = FakeCatFactDetailsViewModel()
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactDetailsFragment>(Bundle(), R.style.AppTheme)


        Espresso
            .onView(withText(R.string.error_msg))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenDataIsSuccessfullyFetched_ThenFactContainerIsDisplayed() {
        //todo

        FakeCatFactDetailsViewModel.shouldMockError = false
//        viewModel = FakeCatFactDetailsViewModel()
        TestCatFactDetailsFragment.testViewModel = viewModel

        fragmentScenario =
            launchFragmentInContainer<TestCatFactDetailsFragment>(Bundle(), R.style.AppTheme)


        Espresso
            .onView(withId(R.id.fact_container))

            .check(matches(isDisplayed()))

    }


}