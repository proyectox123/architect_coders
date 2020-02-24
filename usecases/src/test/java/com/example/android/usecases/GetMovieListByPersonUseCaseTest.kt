package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedPerson
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
class GetMovieListByPersonUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    private lateinit var getMovieListByPersonUseCase: GetMovieListByPersonUseCase

    @Before
    fun setUp(){
        getMovieListByPersonUseCase = GetMovieListByPersonUseCase(movieRepository)
    }

    @Test
    fun `getMovieListByPersonUseCase should return expected success list of movies with given person id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(movie))

            given(movieRepository.getMovieListByPerson(person.id)).willReturn(expectedDataResult)

            //WHEN
            val result = getMovieListByPersonUseCase.invoke(person.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieListByPersonUseCase should return expected error with given person id`(){
        runBlocking {

            //GIVEN
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(movieRepository.getMovieListByPerson(person.id)).willReturn(expectedDataResult)

            //WHEN
            val result = getMovieListByPersonUseCase.invoke(person.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}