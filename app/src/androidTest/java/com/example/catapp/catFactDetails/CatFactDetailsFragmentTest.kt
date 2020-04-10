package com.example.catapp.catFactDetails

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.catapp.R
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before

class CatFactDetailsFragmentTest{
    @MockK
    lateinit var viewModel : CatFactDetailsViewModel

    lateinit var fragmentScenario: FragmentScenario<TestCatFactDetailsFragment>

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        fragmentScenario = launchFragmentInContainer(Bundle(), R.style.AppTheme)
    }
}