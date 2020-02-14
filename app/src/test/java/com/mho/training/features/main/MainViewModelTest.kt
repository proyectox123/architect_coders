package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.usecases.*
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.atLeast
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

/*
[functionName]Should[Call|Return|Throw|Insert|etc][Function|Object|Exception][WithGiven][When]
 */

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase

    @Mock
    lateinit var getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase

    @Mock
    lateinit var getPopularMovieListUseCase: GetPopularMovieListUseCase

    @Mock
    lateinit var getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase

    @Mock
    lateinit var getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerError: Observer<Boolean>

    @Mock
    lateinit var observerEvents: Observer<Event<MainViewModel.Navigation>>

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    @Mock
    lateinit var observerMovieCategory: Observer<MovieCategoryEnum>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
        viewModel = MainViewModel(
            getFavoriteMovieListUseCase,
            getFavoriteMovieListWithChangesUseCase,
            getPopularMovieListUseCase,
            getTopRatedMovieListUseCase,
            getInTheatersMovieListUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `events live data should launches location permission request`() {

        viewModel.events.observeForever(observerEvents)

        viewModel.onMovieListRefresh()

        verify(observerEvents).onChanged(Event(MainViewModel.Navigation.RequestLocationPermission))
    }

    @Test
    fun `loading should show and error should hide after having the permission`() {
        runBlocking {

            //GIVEN
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)

            //WHEN
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerLoading).onChanged(true)
            verify(observerError).onChanged(false)
        }
    }

    @Test
    fun `loading should hide and error should show after not having the permission`() {
        runBlocking {

            //GIVEN
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)

            //WHEN
            viewModel.onCoarsePermissionRequested(false)

            //THEN
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
        }
    }

    @Test
    fun `getTopRatedMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = listOf(movie)

            given(getTopRatedMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResult))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.TOP_RATED)
            verify(observerError).onChanged(false)
            verify(observerLoading).onChanged(false)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getTopRatedMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            val expectedResult = mutableListOf<Movie>()

            given(getTopRatedMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.TOP_RATED)
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getPopularMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = listOf(movie)

            given(getPopularMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResult))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.POPULAR)
            verify(observerError).onChanged(false)
            verify(observerLoading).onChanged(false)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getPopularMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            val expectedResult = mutableListOf<Movie>()

            given(getPopularMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.POPULAR)
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getInTheatersMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = listOf(movie)

            given(getInTheatersMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResult))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieInTheatersListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory, atLeast(1)).onChanged(MovieCategoryEnum.IN_THEATERS)
            verify(observerError).onChanged(false)
            verify(observerLoading).onChanged(false)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getInTheatersMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            val expectedResult = mutableListOf<Movie>()

            given(getInTheatersMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieInTheatersListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory, atLeast(1)).onChanged(MovieCategoryEnum.IN_THEATERS)
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedResult = listOf(movie)

            given(getFavoriteMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResult))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieFavoriteListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.FAVORITE)
            verify(observerError).onChanged(false)
            verify(observerLoading).onChanged(false)
            verify(observerMovies).onChanged(expectedResult)
        }
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            val expectedResult = mutableListOf<Movie>()

            given(getFavoriteMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieFavoriteListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.FAVORITE)
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResult)
        }
    }
}