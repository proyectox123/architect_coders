package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMovieListUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase

    @Before
    fun setUp() {
        getFavoriteMovieListUseCase = GetFavoriteMovieListUseCase(movieRepository)
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected success list of movies`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult: DataResult<List<Movie>> = DataResult.Success(listOf(movie))

            whenever(movieRepository.getFavoriteMovieList()).thenReturn(expectedDataResult)

            // WHEN
            val result: DataResult<List<Movie>> = getFavoriteMovieListUseCase.invoke()

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieListUseCase should return expected error`(){
        runBlocking {

            // GIVEN
            val expectedDataResult: DataResult<List<Movie>> = DataResult.Error(IOException(""))

            whenever(movieRepository.getFavoriteMovieList()).thenReturn(expectedDataResult)

            // WHEN
            val result = getFavoriteMovieListUseCase.invoke()

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}