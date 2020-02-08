package com.example.android.usecases

import com.example.android.data.repositories.TrailerRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedTrailer
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
class GetTrailerListUseCaseTest {

    @Mock
    lateinit var trailerRepository: TrailerRepository

    lateinit var getTrailerListUseCase: GetTrailerListUseCase

    @Before
    fun setUp(){
        getTrailerListUseCase = GetTrailerListUseCase(trailerRepository)
    }

    @Test
    fun `is trailer list invoke success`(){
        runBlocking {

            //GIVEN

            val movie = mockedMovie.copy(id = 1)
            val trailer = mockedTrailer.copy(id = "1")

            val dataResult = DataResult.Success(listOf(trailer))

            whenever(trailerRepository.getTrailerList(movie.id)).thenReturn(dataResult)

            //WHEN

            val result = getTrailerListUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is trailer list invoke fail`(){
        runBlocking {

            //GIVEN

            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(trailerRepository.getTrailerList(movie.id)).thenReturn(dataResult)

            //WHEN

            val result = getTrailerListUseCase.invoke(movie.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}