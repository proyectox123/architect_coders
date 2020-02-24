package com.mho.training.features.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.testshared.defaultFakeFavoriteMovieStatus
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
import com.mho.training.initMockedDi
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerIsFavorite: Observer<Boolean>

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (movie: Movie) -> MovieDetailViewModel(movie, get(), get(), get()) }
            factory { GetFavoriteMovieStatus(get()) }
            factory { UpdateFavoriteMovieStatusUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie)}
    }

    @Test
    fun `onValidateFavoriteMovieStatus should return true when favorite movie status is validate`() {
        runBlocking {

            //GIVEN
            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.onValidateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(defaultFakeFavoriteMovieStatus)
        }
    }

    @Test
    fun `updateFavoriteMovieStatus should return true when favorite movie status is updated`() {
        runBlocking {

            //GIVEN
            viewModel.isFavorite.observeForever(observerIsFavorite)

            //WHEN
            viewModel.updateFavoriteMovieStatus()

            //THEN
            verify(observerIsFavorite).onChanged(defaultFakeFavoriteMovieStatus)
        }
    }
}