package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
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

    @Mock lateinit var getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase
    @Mock lateinit var getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase
    @Mock lateinit var getPopularMovieListUseCase: GetPopularMovieListUseCase
    @Mock lateinit var getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase
    @Mock lateinit var getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase

    @Mock lateinit var loadingObserver: Observer<Boolean>
    @Mock lateinit var observerError: Observer<Boolean>
    @Mock lateinit var observerEvents: Observer<Event<MainViewModel.Navigation>>
    @Mock lateinit var observerMovies: Observer<List<Movie>>
    @Mock lateinit var observerMovieCategory: Observer<MovieCategoryEnum>

    private lateinit var movie: Movie
    private lateinit var expectedResultMovieList: List<Movie>
    private lateinit var expectedResultMovieListEmpty: List<Movie>

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

        movie = mockedMovie.copy(id = 1)
        expectedResultMovieList = listOf(movie)
        expectedResultMovieListEmpty = emptyList()
    }

    @Test
    fun `onMovieListRefresh should launches location permission request`() {

        //GIVEN
        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieListRefresh()

        //THEN
        verify(observerEvents).onChanged(Event(MainViewModel.Navigation.RequestLocationPermission))
    }

    @Test
    fun `onCoarsePermissionRequested should sets loading as true and error as false when argument passed is true`() {
        runBlocking {

            //GIVEN
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)

            //WHEN
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(loadingObserver).onChanged(true)
            verify(observerError).onChanged(false)
        }
    }

    @Test
    fun `onCoarsePermissionRequested should sets loading as false and error as true when argument passed is false`() {
        runBlocking {

            //GIVEN
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)

            //WHEN
            viewModel.onCoarsePermissionRequested(false)

            //THEN
            verify(loadingObserver).onChanged(false)
            verify(observerError).onChanged(true)
        }
    }

    @Test
    fun `onMovieHighestRatedListRefresh should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN

            given(getTopRatedMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResultMovieList))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.TOP_RATED)
            verify(observerError).onChanged(false)
            verify(loadingObserver).onChanged(false)
            verify(observerMovies).onChanged(expectedResultMovieList)
        }
    }

    @Test
    fun `getTopRatedMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            given(getTopRatedMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.TOP_RATED)
            verify(loadingObserver).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResultMovieListEmpty)
        }
    }

    @Test
    fun `getPopularMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            given(getPopularMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResultMovieList))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.POPULAR)
            verify(observerError).onChanged(false)
            verify(loadingObserver).onChanged(false)
            verify(observerMovies).onChanged(expectedResultMovieList)
        }
    }

    @Test
    fun `getPopularMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            given(getPopularMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.POPULAR)
            verify(loadingObserver).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResultMovieListEmpty)
        }
    }

    @Test
    fun `getInTheatersMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            given(getInTheatersMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResultMovieList))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieInTheatersListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory, atLeast(1)).onChanged(MovieCategoryEnum.IN_THEATERS)
            verify(observerError).onChanged(false)
            verify(loadingObserver).onChanged(false)
            verify(observerMovies).onChanged(expectedResultMovieList)
        }
    }

    @Test
    fun `getInTheatersMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            given(getInTheatersMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieInTheatersListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory, atLeast(1)).onChanged(MovieCategoryEnum.IN_THEATERS)
            verify(loadingObserver).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResultMovieListEmpty)
        }
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected success list of movies after having the permission`() {
        runBlocking {

            //GIVEN
            given(getFavoriteMovieListUseCase.invoke()).willReturn(DataResult.Success(expectedResultMovieList))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieFavoriteListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.FAVORITE)
            verify(observerError).onChanged(false)
            verify(loadingObserver).onChanged(false)
            verify(observerMovies).onChanged(expectedResultMovieList)
        }
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected error after having the permission`() {
        runBlocking {

            //GIVEN
            given(getFavoriteMovieListUseCase.invoke()).willReturn(DataResult.Error(IOException("")))

            viewModel.movieCategory.observeForever(observerMovieCategory)
            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(loadingObserver)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieFavoriteListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovieCategory).onChanged(MovieCategoryEnum.FAVORITE)
            verify(loadingObserver).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResultMovieListEmpty)
        }
    }

    @Test
    fun `onMovieClicked should open movie detail with given movie`(){
        //GIVEN
        viewModel.events.observeForever(observerEvents)

        //WHEN
        viewModel.onMovieClicked(movie)

        //THEN
        verify(observerEvents).onChanged(Event(MainViewModel.Navigation.NavigateToMovie(movie)))
    }

    //TODO Test for favoriteMovies LiveData
}