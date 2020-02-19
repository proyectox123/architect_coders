package com.mho.training.features.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedReview
import com.example.android.usecases.GetReviewListUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class ReviewsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getReviewListUseCase: GetReviewListUseCase

    @Mock lateinit var observerHasNotReviews: Observer<Boolean>
    @Mock lateinit var observerLoadingReviews: Observer<Boolean>
    @Mock lateinit var observerReviews: Observer<List<Review>>

    private lateinit var movie: Movie

    private lateinit var viewModel: ReviewsViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = ReviewsViewModel(
            movie.id,
            getReviewListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onReviewsFromMovie should display expected success review list with given movie id`() {
        runBlocking {

            //GIVEN
            val review = mockedReview.copy(id = "")

            val expectedResult = listOf(review)

            given(getReviewListUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.reviews.observeForever(observerReviews)
            viewModel.hasNotReviews.observeForever(observerHasNotReviews)
            viewModel.loadingReviews.observeForever(observerLoadingReviews)

            //WHEN
            viewModel.onReviewsFromMovie()

            //THEN
            verify(observerReviews).onChanged(expectedResult)
            verify(observerHasNotReviews).onChanged(false)
            verify(observerLoadingReviews).onChanged(false)
        }
    }

    @Test
    fun `onReviewsFromMovie should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val expectedResult = emptyList<Review>()

            given(getReviewListUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.reviews.observeForever(observerReviews)
            viewModel.hasNotReviews.observeForever(observerHasNotReviews)
            viewModel.loadingReviews.observeForever(observerLoadingReviews)

            //WHEN
            viewModel.onReviewsFromMovie()

            //THEN
            verify(observerReviews).onChanged(expectedResult)
            verify(observerHasNotReviews).onChanged(true)
            verify(observerLoadingReviews).onChanged(false)
        }
    }

}