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
class GetFavoriteMovieStatusTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getFavoriteMovieStatus: GetFavoriteMovieStatus

    @Before
    fun setUp() {
        getFavoriteMovieStatus = GetFavoriteMovieStatus(movieRepository)
    }

    @Test
    fun `check if selected movie is favorite`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(movieRepository.getFavoriteMovieStatus(movie)).thenReturn(true)

            // WHEN
            val result: Boolean = getFavoriteMovieStatus.invoke(movie)

            // THEN
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun `check if selected movie is not favorite`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            whenever(movieRepository.getFavoriteMovieStatus(movie)).thenReturn(false)

            // WHEN
            val result: Boolean = getFavoriteMovieStatus.invoke(movie)

            // THEN
            Assert.assertEquals(false, result)
        }
    }
}