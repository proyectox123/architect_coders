package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
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

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMovieStatusTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    private lateinit var getFavoriteMovieStatus: GetFavoriteMovieStatus

    @Before
    fun setUp() {
        getFavoriteMovieStatus = GetFavoriteMovieStatus(movieRepository)
    }

    @Test
    fun `getFavoriteMovieStatus should return expected success true with given favorite movie`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = true

            whenever(movieRepository.getFavoriteMovieStatus(movie)).thenReturn(expectedDataResult)

            // WHEN
            val result: Boolean = getFavoriteMovieStatus.invoke(movie)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getFavoriteMovieStatus should return expected success true with given not favorite movie`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = false

            whenever(movieRepository.getFavoriteMovieStatus(movie)).thenReturn(expectedDataResult)

            // WHEN
            val result: Boolean = getFavoriteMovieStatus.invoke(movie)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}