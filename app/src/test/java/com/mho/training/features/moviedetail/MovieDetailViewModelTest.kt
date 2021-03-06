package com.mho.training.features.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
import com.mho.training.utils.Event
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

/*
[functionName]Should[Call|Return|Throw|Insert|etc][Function|Object|Exception][WithGiven][When]
 */

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getFavoriteMovieStatus: GetFavoriteMovieStatus
    @Mock lateinit var updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase

    @Mock lateinit var observerIsFavorite: Observer<Boolean>

    @Mock lateinit var observerEvents: Observer<Event<MovieDetailViewModel.Navigation>>

    private lateinit var movie: Movie

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = MovieDetailViewModel(
            movie,
            getFavoriteMovieStatus,
            updateFavoriteMovieStatusUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onMovieInformation should close activity when movie is null`(){

        //GIVEN
        viewModel = MovieDetailViewModel(
            null,
            getFavoriteMovieStatus,
            updateFavoriteMovieStatusUseCase,
            Dispatchers.Unconfined
        )

        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieValidation()

        //THEN
        verify(observerEvents).onChanged(Event(MovieDetailViewModel.Navigation.CloseActivity))
    }

    @Test
    fun `onMovieInformation should initialize movie detail when movie is valid`(){

        //GIVEN
        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieValidation()

        //THEN
        verify(observerEvents).onChanged(Event(MovieDetailViewModel.Navigation.InitializeMovieDetail(movie)))
    }

    @Test
    fun `onValidateFavoriteMovieStatus should update movie favorite status to true with given movie`() {
        runBlocking {

            //GIVEN
            given(getFavoriteMovieStatus.invoke(movie)).willReturn(true)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(true)
        }
    }

    @Test
    fun `onValidateFavoriteMovieStatus should update movie favorite status to false with given movie`() {
        runBlocking {

            //GIVEN
            given(getFavoriteMovieStatus.invoke(movie)).willReturn(false)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(false)
        }
    }

    @Test
    fun `updateFavoriteMovieStatus should return true with given movie`() {
        runBlocking {

            //GIVEN
            given(updateFavoriteMovieStatusUseCase.invoke(movie)).willReturn(true)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(true)
        }
    }

    @Test
    fun `updateFavoriteMovieStatus should return false with given movie`() {
        runBlocking {

            //GIVEN
            given(updateFavoriteMovieStatusUseCase.invoke(movie)).willReturn(false)

            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(false)
        }
    }
}