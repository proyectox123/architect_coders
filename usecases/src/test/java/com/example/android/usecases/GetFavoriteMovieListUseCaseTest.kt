package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
    fun `is favorite movie list invoke success`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val dataResult: DataResult<List<Movie>> = DataResult.Success(listOf(movie))

            whenever(movieRepository.getFavoriteMovieList()).thenReturn(dataResult)

            // WHEN
            val result: DataResult<List<Movie>> = getFavoriteMovieListUseCase.invoke()

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is favorite movie list invoke fail`() {
        runBlocking {

            // GIVEN
            val dataResult: DataResult<List<Movie>> = DataResult.Error(IOException(""))

            whenever(movieRepository.getFavoriteMovieList()).thenReturn(dataResult)

            // WHEN
            val result = getFavoriteMovieListUseCase.invoke()

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }
}