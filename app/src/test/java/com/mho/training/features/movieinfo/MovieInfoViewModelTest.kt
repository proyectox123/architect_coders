package com.mho.training.features.movieinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedMovieDetail
import com.example.android.usecases.GetMovieDetailByIdUseCase
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
class MovieInfoViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase

    @Mock lateinit var observerInfoMovie: Observer<Movie>
    @Mock lateinit var observerMovieDetail: Observer<MovieDetail>

    private lateinit var movie: Movie

    private lateinit var viewModel: MovieInfoViewModel

    @Before
    fun setUp(){
        movie = mockedMovie.copy(id = 1)

        viewModel = MovieInfoViewModel(
            movie,
            getMovieDetailByIdUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `onMovieInformation should show movie information with given movie`(){

        //GIVEN
        val expectedResult = movie

        viewModel.infoMovie.observeForever(observerInfoMovie)

        //WHEN
        viewModel.onMovieInformation()

        //THEN
        verify(observerInfoMovie).onChanged(expectedResult)
    }

    @Test
    fun `onMovieInformation should return expected success movie detail with given movie id`() {
        runBlocking {

            //GIVEN
            val expectedResult = mockedMovieDetail.copy(id = 1)

            given(getMovieDetailByIdUseCase.invoke(movie.id)).willReturn(DataResult.Success(expectedResult))

            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerMovieDetail).onChanged(expectedResult)
        }
    }

    @Test
    fun `onMovieInformation should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            given(getMovieDetailByIdUseCase.invoke(movie.id)).willReturn(
                DataResult.Error(
                    IOException("")
                ))

            viewModel.movieDetail.observeForever(observerMovieDetail)

            //WHEN
            viewModel.onMovieInformation()

            //THEN
            verify(observerMovieDetail).onChanged(null)
        }
    }

}