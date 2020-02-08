package com.example.android.usecases

import com.example.android.data.repositories.CreditRepository
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedCredit
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
class GetCreditListUseCaseTest {

    @Mock
    lateinit var creditRepository: CreditRepository

    lateinit var getCreditListUseCase: GetCreditListUseCase

    @Before
    fun setUp() {
        getCreditListUseCase = GetCreditListUseCase(creditRepository)
    }

    @Test
    fun `is credit list invoke success`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val credit = mockedCredit.copy(id = 1)

            val dataResult: DataResult<List<Credit>> = DataResult.Success(listOf(credit))

            whenever(creditRepository.getCreditList(movie.id)).thenReturn(dataResult)

            // WHEN
            val result = getCreditListUseCase.invoke(movie.id)

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is credit list invoke fail`() {
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult: DataResult<List<Credit>> = DataResult.Error(IOException(""))

            whenever(creditRepository.getCreditList(movie.id)).thenReturn(dataResult)

            // WHEN
            val result = getCreditListUseCase.invoke(movie.id)

            // THEN
            Assert.assertEquals(dataResult, result)
        }
    }
}