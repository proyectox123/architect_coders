package com.example.android.usecases

import com.example.android.data.repositories.KeywordRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedKeyword
import com.example.android.mocks.mockedMovie
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
class GetKeywordListUseCaseTest {

    @Mock
    lateinit var keywordRepository: KeywordRepository

    lateinit var getKeywordListUseCase: GetKeywordListUseCase

    @Before
    fun setUp(){
        getKeywordListUseCase = GetKeywordListUseCase(keywordRepository)
    }

    @Test
    fun `getKeywordListUseCase should return expected success list of keywords with given movie id`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val keyword = mockedKeyword.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(keyword))

            given(keywordRepository.getKeywordList(movie.id)).willReturn(expectedDataResult)

            // WHEN
            val result = getKeywordListUseCase.invoke(movie.id)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getKeywordListUseCase should return expected error with given movie id`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(keywordRepository.getKeywordList(movie.id)).willReturn(expectedDataResult)

            // WHEN
            val result = getKeywordListUseCase.invoke(movie.id)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}