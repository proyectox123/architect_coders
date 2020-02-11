package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.usecases.*
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
    fun `observing LiveData launches location permission request`() {

        viewModel.events.observeForever(observerEvents)

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

            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
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

            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
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

            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
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

            viewModel.error.observeForever(observerError)
            viewModel.loading.observeForever(observerLoading)
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerLoading).onChanged(false)
            verify(observerError).onChanged(true)
            verify(observerMovies).onChanged(expectedResult)
        }
    }
}