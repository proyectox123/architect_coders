package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedMovieDetail
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
class GetMovieDetailByIdUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase

    @Before
    fun setUp(){
        getMovieDetailByIdUseCase = GetMovieDetailByIdUseCase(movieRepository)
    }

    @Test
    fun `is movie detail by id invoke success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val movieDetail = mockedMovieDetail.copy(id = 1)

            val dataResult = DataResult.Success(movieDetail)

            whenever(movieRepository.getMovieDetailById(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = getMovieDetailByIdUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is movie detail by id invoke fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(movieRepository.getMovieDetailById(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = getMovieDetailByIdUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}