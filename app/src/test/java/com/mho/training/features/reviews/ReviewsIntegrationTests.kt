package com.mho.training.features.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Review
import com.example.android.testshared.defaultFakeReviews
import com.example.android.testshared.mockedMovie
import com.mho.training.di.DaggerTestComponent
import com.mho.training.di.TestComponent
import com.mho.training.features.reviews.di.ReviewsFragmentModule
import com.mho.training.rules.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReviewsIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerReviews: Observer<List<Review>>

    private lateinit var viewModel: ReviewsViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(ReviewsFragmentModule(mockedMovie.id)).reviewsViewModel
    }

    @Test
    fun `onKeywordsFromMovie should load success credit list`() {
        runBlocking {
            //GIVEN
            viewModel.reviews.observeForever(observerReviews)

            //WHEN
            viewModel.onReviewsFromMovie()

            //THEN
            verify(observerReviews).onChanged(defaultFakeReviews)
        }
    }
}