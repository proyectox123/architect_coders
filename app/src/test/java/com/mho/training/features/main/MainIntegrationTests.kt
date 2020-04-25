package com.mho.training.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.defaultFakeMovies
import com.mho.training.di.DaggerTestComponent
import com.mho.training.di.TestComponent
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
class MainIntegrationTests {

    @ExperimentalCoroutinesApi
    @get:Rule var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var viewModel: MainViewModel

    private val component: TestComponent = DaggerTestComponent.factory().create()

    @Before
    fun setUp() {
        viewModel = component.plus(MainActivityModule()).mainViewModel
    }

    @ExperimentalCoroutinesApi
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

    @ExperimentalCoroutinesApi
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