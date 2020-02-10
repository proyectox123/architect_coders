package com.example.android.data.repositories

import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedTrailer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

    lateinit var trailerRepository: TrailerRepository

    @Before
    fun setUp(){
        trailerRepository = TrailerRepository(remoteTrailerDataSource)
    }

    @Test
    fun `is getTrailerList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val trailer = mockedTrailer.copy(id = "1")

            val dataResult = DataResult.Success(listOf(trailer))

            whenever(remoteTrailerDataSource.getTrailerList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = trailerRepository.getTrailerList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getTrailerList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteTrailerDataSource.getTrailerList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = trailerRepository.getTrailerList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

}