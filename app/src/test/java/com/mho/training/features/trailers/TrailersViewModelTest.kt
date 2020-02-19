package com.mho.training.features.trailers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedTrailer
import com.example.android.usecases.GetTrailerListUseCase
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
class TrailersViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getTrailerListUseCase: GetTrailerListUseCase

    @Mock lateinit var observerHasNotTrailers: Observer<Boolean>
    @Mock lateinit var observerLoadingTrailers: Observer<Boolean>
    @Mock lateinit var observerTrailers: Observer<List<Trailer>>

    private lateinit var movie: Movie

    private lateinit var viewModel: TrailersViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = TrailersViewModel(
            movie.id,
            getTrailerListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onTrailersFromMovie should display expected success trailer list with given movie id`() {
        runBlocking {

            //GIVEN
            val trailer = mockedTrailer.copy(id = "")

            val expectedResult = listOf(trailer)

            given(getTrailerListUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.trailers.observeForever(observerTrailers)
            viewModel.hasNotTrailers.observeForever(observerHasNotTrailers)
            viewModel.loadingTrailers.observeForever(observerLoadingTrailers)

            //WHEN
            viewModel.onTrailersFromMovie()

            //THEN
            verify(observerTrailers).onChanged(expectedResult)
            verify(observerHasNotTrailers).onChanged(false)
            verify(observerLoadingTrailers).onChanged(false)
        }
    }

    @Test
    fun `onTrailersFromMovie should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val expectedResult = emptyList<Trailer>()

            given(getTrailerListUseCase.invoke(movie.id)).willReturn(DataResult.Error(IOException("")))

            viewModel.trailers.observeForever(observerTrailers)
            viewModel.hasNotTrailers.observeForever(observerHasNotTrailers)
            viewModel.loadingTrailers.observeForever(observerLoadingTrailers)

            //WHEN
            viewModel.onTrailersFromMovie()

            //THEN
            verify(observerTrailers).onChanged(expectedResult)
            verify(observerHasNotTrailers).onChanged(true)
            verify(observerLoadingTrailers).onChanged(false)
        }
    }
}