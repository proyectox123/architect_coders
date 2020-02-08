package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateFavoriteMovieStatusUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase

    @Before
    fun setUp() {
        updateFavoriteMovieStatusUseCase = UpdateFavoriteMovieStatusUseCase(movieRepository)
    }

    @Test
    fun `become selected movie favorite`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(movieRepository.updateFavoriteMovieStatus(movie)).thenReturn(true)

            // WHEN
            val result: Boolean = updateFavoriteMovieStatusUseCase.invoke(movie)

            // THEN
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun `become selected movie not favorite`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(movieRepository.updateFavoriteMovieStatus(movie)).thenReturn(false)

            // WHEN
            val result: Boolean = updateFavoriteMovieStatusUseCase.invoke(movie)

            // THEN
            Assert.assertEquals(false, result)
        }
    }
}