package com.example.android.data.repositories

import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedTrailer
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
class TrailerRepositoryTest {

    @Mock
    lateinit var remoteTrailerDataSource: RemoteTrailerDataSource

    private lateinit var trailerRepository: TrailerRepository

    @Before
    fun setUp(){
        trailerRepository = TrailerRepository(remoteTrailerDataSource)
    }

    @Test
    fun `getTrailerList from remote data source should return expected success list of trailers with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val trailer = mockedTrailer.copy(id = "1")

            val expectedDataResult = DataResult.Success(listOf(trailer))

            given(remoteTrailerDataSource.getTrailerList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = trailerRepository.getTrailerList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getTrailerList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteTrailerDataSource.getTrailerList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = trailerRepository.getTrailerList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}