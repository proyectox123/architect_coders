package com.example.android.data.repositories

import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedCredit
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
class CreditRepositoryTest {

    @Mock
    lateinit var remoteCreditDataSource: RemoteCreditDataSource

    lateinit var creditRepository: CreditRepository

    @Before
    fun setUp() {
        creditRepository = CreditRepository(remoteCreditDataSource)
    }


    @Test
    fun `is getCreditList by remote data source success`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val credit = mockedCredit.copy(id = 1)

            val dataResult = DataResult.Success(listOf(credit))

            whenever(remoteCreditDataSource.getCreditList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = creditRepository.getCreditList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getCreditList by remote data source fail`() {
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remoteCreditDataSource.getCreditList(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = creditRepository.getCreditList(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

}