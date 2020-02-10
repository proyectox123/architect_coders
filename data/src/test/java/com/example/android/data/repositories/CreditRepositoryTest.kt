package com.example.android.data.repositories

import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedCredit
import com.example.android.mocks.mockedMovie
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class CreditRepositoryTest {

    @Mock
    lateinit var remoteCreditDataSource: RemoteCreditDataSource

    lateinit var creditRepository: CreditRepository

    @Before
    fun setUp() {
        creditRepository = CreditRepository(remoteCreditDataSource)
    }

    @Test
    fun `getCreditList from remote data source should return expected success list of credit with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val credit = mockedCredit.copy(id = 1)

            val expectedDataResult = DataResult.Success(listOf(credit))

            given(remoteCreditDataSource.getCreditList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = creditRepository.getCreditList(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getCreditList from remote data source should return expected error with given movie id`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remoteCreditDataSource.getCreditList(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = creditRepository.getCreditList(movie.id)

            //THEN
            assertEquals(expectedDataResult, result)
        }
    }

}