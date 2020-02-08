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
class GetTopRatedMovieListUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase

    @Before
    fun setUp(){
        getTopRatedMovieListUseCase = GetTopRatedMovieListUseCase(movieRepository)
    }

    @Test
    fun `is top rated movie invoke success`(){
        runBlocking {

            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(movieRepository.getTopRatedMovieList()).thenReturn(dataResult)

            //WHEN

            val result = getTopRatedMovieListUseCase.invoke()

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is top rated movie invoke fail`(){
        runBlocking {

            //GIVEN

            val dataResult = DataResult.Error(IOException(""))

            whenever(movieRepository.getTopRatedMovieList()).thenReturn(dataResult)

            //WHEN

            val result = getTopRatedMovieListUseCase.invoke()

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}