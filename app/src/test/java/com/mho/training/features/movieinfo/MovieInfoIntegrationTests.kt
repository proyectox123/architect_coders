package com.mho.training.features.movieinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.testshared.defaultFakeMovieDetail
import com.example.android.testshared.mockedMovie
import com.example.android.usecases.GetMovieDetailByIdUseCase
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
class MovieInfoIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observerInfoMovie: Observer<Movie>
    @Mock lateinit var observerMovieDetail: Observer<MovieDetail>

    private lateinit var viewModel: MovieInfoViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (movie: Movie) -> MovieInfoViewModel(movie, get(), get()) }
            factory { GetMovieDetailByIdUseCase(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(mockedMovie) }
    }

    @Test
    fun `onMovieInformation should display movie detail information`() {
        runBlocking {

            //GIVEN
            viewModel.infoMovie.observeForever(observerInfoMovie)
            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerInfoMovie).onChanged(mockedMovie)
            verify(observerMovieDetail).onChanged(defaultFakeMovieDetail)
        }
    }
}