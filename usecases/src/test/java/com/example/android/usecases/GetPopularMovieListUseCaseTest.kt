package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.nhaarman.mockitokotlin2.given
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
class GetPopularMovieListUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    private lateinit var getPopularMovieListUseCase: GetPopularMovieListUseCase

    @Before
    fun setUp() {
        getPopularMovieListUseCase = GetPopularMovieListUseCase(movieRepository)
    }

    @Test
    fun `getPopularMovieListUseCase should return expected success list of movies`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(movieRepository.getPopularMovieList()).willReturn(expectedDataResult)

            // WHEN
            val result = getPopularMovieListUseCase.invoke()

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getPopularMovieListUseCase should return expected error`(){
        runBlocking {

            // GIVEN
            val expectedDataResult = DataResult.Error(IOException(""))

            given(movieRepository.getPopularMovieList()).willReturn(expectedDataResult)

            // WHEN
            val result = getPopularMovieListUseCase.invoke()

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}