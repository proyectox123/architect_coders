package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedPerson
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
class GetMovieListByPersonUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getMovieListByPersonUseCase: GetMovieListByPersonUseCase

    @Before
    fun setUp(){
        getMovieListByPersonUseCase = GetMovieListByPersonUseCase(movieRepository)
    }

    @Test
    fun `is movie list by person invoke success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Success(listOf(movie))

            whenever(movieRepository.getMovieListByPerson(person.id)).thenReturn(dataResult)

            //WHEN
            val result = getMovieListByPersonUseCase.invoke(person.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is movie list by person invoke fail`(){
        runBlocking {

            //GIVEN
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(movieRepository.getMovieListByPerson(person.id)).thenReturn(dataResult)

            //WHEN
            val result = getMovieListByPersonUseCase.invoke(person.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}