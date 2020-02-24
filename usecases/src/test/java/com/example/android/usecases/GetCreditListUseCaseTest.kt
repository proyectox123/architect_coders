package com.example.android.usecases

import com.example.android.data.repositories.CreditRepository
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult
import com.example.android.testshared.mockedCredit
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
class GetCreditListUseCaseTest {

    @Mock
    lateinit var creditRepository: CreditRepository

    private lateinit var getCreditListUseCase: GetCreditListUseCase

    @Before
    fun setUp() {
        getCreditListUseCase = GetCreditListUseCase(creditRepository)
    }

    @Test
    fun `getCreditListUseCase should return expected success list of credits with given movie id`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)
            val credit = mockedCredit.copy(id = 1)

            val expectedDataResult: DataResult<List<Credit>> = DataResult.Success(listOf(credit))

            given(creditRepository.getCreditList(movie.id)).willReturn(expectedDataResult)

            // WHEN
            val result = getCreditListUseCase.invoke(movie.id)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getCreditListUseCase should return expected error with given movie id`(){
        runBlocking {

            // GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult: DataResult<List<Credit>> = DataResult.Error(IOException(""))

            given(creditRepository.getCreditList(movie.id)).willReturn(expectedDataResult)

            // WHEN
            val result = getCreditListUseCase.invoke(movie.id)

            // THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }
}