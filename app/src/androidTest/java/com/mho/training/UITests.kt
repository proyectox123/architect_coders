package com.mho.training

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.mho.training.features.main.MainActivity
import com.mho.training.rules.MockWebServerRule
import com.mho.training.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UITests: KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Before
    fun setUp() {
        val response: MockResponse = MockResponse().fromJson(
            ApplicationProvider.getApplicationContext(),
            "intheatersmovies.json"
        )
        mockWebServerRule.server.enqueue(response)

        val resource = OkHttp3IdlingResource.create("okhttp", get<MovieRequest>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.rvMovieList))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvMovieList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        val textView = onView(
            Matchers.allOf(
                withId(R.id.movieDetailTitleTextView), withText("Sonic the Hedgehog"),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Sonic the Hedgehog")))

    }
}