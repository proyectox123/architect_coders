package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
    fun `updateFavoriteMovieStatusUseCase should return true when update favorite movie status with given movie`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = true

            given(movieRepository.updateFavoriteMovieStatus(movie)).willReturn(expectedDataResult)

            // WHEN
            val result: Boolean = updateFavoriteMovieStatusUseCase.invoke(movie)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `updateFavoriteMovieStatusUseCase should return false when update favorite movie status with given movie`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = false

            given(movieRepository.updateFavoriteMovieStatus(movie)).willReturn(expectedDataResult)

            // WHEN
            val result: Boolean = updateFavoriteMovieStatusUseCase.invoke(movie)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}