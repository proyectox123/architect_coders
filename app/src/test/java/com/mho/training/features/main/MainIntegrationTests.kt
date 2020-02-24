package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.defaultFakeMovies
import com.example.android.usecases.*
import com.mho.training.initMockedDi
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get(), get(), get(), get(), get()) }
            factory { GetFavoriteMovieListUseCase(get()) }
            factory { GetFavoriteMovieListWithChangesUseCase(get()) }
            factory { GetPopularMovieListUseCase(get()) }
            factory { GetTopRatedMovieListUseCase(get()) }
            factory { GetInTheatersMovieListUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get()
    }

    @Test
    fun `onMovieHighestRatedListRefresh should load success movie list when permission requested is valid`() {
        runBlocking {

            //GIVEN
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieHighestRatedListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovies).onChanged(defaultFakeMovies)
        }
    }

    @Test
    fun `onMovieInTheatersListRefresh should load success movie list when permission requested is valid`() {
        runBlocking {

            //GIVEN
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieInTheatersListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovies).onChanged(defaultFakeMovies)
        }
    }

    @Test
    fun `onMoviePopularListRefresh should load success movie list when permission requested is valid`() {
        runBlocking {

            //GIVEN
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMoviePopularListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovies).onChanged(defaultFakeMovies)
        }
    }

    @Test
    fun `onMovieFavoriteListRefresh should load success movie list when permission requested is valid`() {
        runBlocking {

            //GIVEN
            viewModel.movies.observeForever(observerMovies)

            //WHEN
            viewModel.onMovieFavoriteListRefresh()
            viewModel.onCoarsePermissionRequested(true)

            //THEN
            verify(observerMovies).onChanged(defaultFakeMovies)
        }
    }
}