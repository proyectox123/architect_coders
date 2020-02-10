package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
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
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetTopRatedMovieListUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase

    @Before
    fun setUp(){
        getTopRatedMovieListUseCase = GetTopRatedMovieListUseCase(movieRepository)
    }

    @Test
    fun `getTopRatedMovieListUseCase should return expected success list of movies`(){
        runBlocking {

            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(movieRepository.getTopRatedMovieList()).willReturn(expectedDataResult)

            //WHEN

            val result = getTopRatedMovieListUseCase.invoke()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getTopRatedMovieListUseCase should return expected error`(){
        runBlocking {

            //GIVEN

            val expectedDataResult = DataResult.Error(IOException(""))

            given(movieRepository.getTopRatedMovieList()).willReturn(expectedDataResult)

            //WHEN

            val result = getTopRatedMovieListUseCase.invoke()

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}