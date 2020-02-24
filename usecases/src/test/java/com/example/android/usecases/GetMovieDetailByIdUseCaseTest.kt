package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedMovie
import com.example.android.testshared.mockedMovieDetail
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
class GetMovieDetailByIdUseCaseTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    private lateinit var getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase

    @Before
    fun setUp(){
        getMovieDetailByIdUseCase = GetMovieDetailByIdUseCase(movieRepository)
    }

    @Test
    fun `getMovieDetailById should return expected success movie detail with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val movieDetail = mockedMovieDetail.copy(id = 1)

            val expectedDataResult = DataResult.Success(movieDetail)

            given(movieRepository.getMovieDetailById(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = getMovieDetailByIdUseCase.invoke(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getMovieDetailById should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(movieRepository.getMovieDetailById(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = getMovieDetailByIdUseCase.invoke(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}