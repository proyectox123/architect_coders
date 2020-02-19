package com.example.android.data.repositories

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedKeyword
import com.example.android.testshared.mockedMovie
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
class KeywordRepositoryTest {

    @Mock
    lateinit var remoteKeywordDataSource: RemoteKeywordDataSource

    private lateinit var keywordRepository: KeywordRepository

    @Before
    fun setUp(){
        keywordRepository = KeywordRepository(remoteKeywordDataSource)
    }

    @Test
    fun `getKeywordList from remote data source should return expected success list of keyword with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val keyword = mockedKeyword.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(keyword))

            given(remoteKeywordDataSource.getKeywordList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = keywordRepository.getKeywordList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getKeywordList from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteKeywordDataSource.getKeywordList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = keywordRepository.getKeywordList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}