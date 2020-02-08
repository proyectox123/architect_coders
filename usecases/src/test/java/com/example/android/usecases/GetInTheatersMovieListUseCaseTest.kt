package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
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
class GetInTheatersMovieListUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase

    @Before
    fun setUp() {
        getInTheatersMovieListUseCase = GetInTheatersMovieListUseCase(movieRepository)
    }

    @Test
    fun `is in theaters movie list invoke success`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val dataResult = DataResult.Success(listOf(movie))

            whenever(movieRepository.getInTheatersMovieList()).thenReturn(dataResult)

            // WHEN
            val result = getInTheatersMovieListUseCase.invoke()

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is in theaters movie list invoke fail`() {
        runBlocking {

            // GIVEN
            val dataResult = DataResult.Error(IOException(""))

            whenever(movieRepository.getInTheatersMovieList()).thenReturn(dataResult)

            // WHEN
            val result = getInTheatersMovieListUseCase.invoke()

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }
}