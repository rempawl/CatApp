package com.example.catapp.data.errorModel

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.runner.AndroidJUnit4
import com.example.catapp.MyApp
import com.example.catapp.R
import com.example.catapp.testUtils.Utils.RESPONSE_ERROR_404
import com.example.catapp.utils.providers.ResourcesProvider
import com.example.catapp.utils.providers.ResourcesProviderImpl
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorModelImplTest {
    lateinit var resourcesProvider: ResourcesProvider

    lateinit var errorModel: ErrorModel

    private val context = ApplicationProvider.getApplicationContext<MyApp>()

    @Before
    fun setUp() {
        resourcesProvider = ResourcesProviderImpl(context)
        errorModel = ErrorModelImpl(resourcesProvider)
    }

    @Test
    fun whenErrorCodeIs404ThenResourcesProviderReturnsNotFoundString() {
        val actual = errorModel.getErrorMessage(RESPONSE_ERROR_404)
        val expected = context.getString(R.string.not_found_error)
    }

    @Test
    fun whenErrorCodeIs403ThenResourcesProviderReturnsForbiddenString() {
        val actual = errorModel.getErrorMessage(RESPONSE_ERROR_404)
        val expected = context.getString(R.string.forbidden_error)
        assertThat(actual, `is`(expected))
    }

    @Test
    fun whenErrorCodeIs400ThenResourcesProviderReturnsBadRequestString() {
        val actual = errorModel.getErrorMessage(RESPONSE_ERROR_404)
        val expected = context.getString(R.string.bad_request_error)
        assertThat(actual, `is`(expected))

    }

    @Test
    fun whenErrorCodeIs500ThenResourcesProviderReturnsServerErrorString() {
        val actual = errorModel.getErrorMessage(RESPONSE_ERROR_404)
        val expected = context.getString(R.string.server_error)
        assertThat(actual, `is`(expected))

    }


}