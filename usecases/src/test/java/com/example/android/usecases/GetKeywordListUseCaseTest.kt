package com.example.android.usecases

import com.example.android.data.repositories.KeywordRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedKeyword
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
class GetKeywordListUseCaseTest {

    @Mock
    lateinit var keywordRepository: KeywordRepository

    lateinit var getKeywordListUseCase: GetKeywordListUseCase

    @Before
    fun setUp(){
        getKeywordListUseCase = GetKeywordListUseCase(keywordRepository)
    }

    @Test
    fun `is keyword list invoke success`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val keyword = mockedKeyword.copy(id = 1)
            val dataResult = DataResult.Success(listOf(keyword))

            whenever(keywordRepository.getKeywordList(movie.id)).thenReturn(dataResult)

            // WHEN
            val result = getKeywordListUseCase.invoke(movie.id)

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is keyword list invoke fail`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val dataResult = DataResult.Error(IOException(""))

            whenever(keywordRepository.getKeywordList(movie.id)).thenReturn(dataResult)

            // WHEN
            val result = getKeywordListUseCase.invoke(movie.id)

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}