package com.example.android.data.repositories

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedKeyword
import com.example.android.mocks.mockedMovie
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
class KeywordRepositoryTest {

    @Mock
    lateinit var remoteKeywordDataSource: RemoteKeywordDataSource

    lateinit var keywordRepository: KeywordRepository

    @Before
    fun setUp(){
        keywordRepository = KeywordRepository(remoteKeywordDataSource)
    }

    @Test
    fun `is getKeywordList from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val keyword = mockedKeyword.copy(id = 1)

            val dataResult = DataResult.Success(listOf(keyword))

            whenever(remoteKeywordDataSource.getKeywordList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = keywordRepository.getKeywordList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getKeywordList from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteKeywordDataSource.getKeywordList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = keywordRepository.getKeywordList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

}