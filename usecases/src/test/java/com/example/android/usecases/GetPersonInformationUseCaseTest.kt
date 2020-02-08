package com.example.android.usecases

import com.example.android.data.repositories.PersonRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedCredit
import com.example.android.mocks.mockedPerson
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
class GetPersonInformationUseCaseTest {

    @Mock
    lateinit var personRepository: PersonRepository

    lateinit var getPersonInformationUseCase: GetPersonInformationUseCase

    @Before
    fun setUp(){
        getPersonInformationUseCase = GetPersonInformationUseCase(personRepository)
    }

    @Test
    fun `is person invoke success`(){
        runBlocking {

            //GIVEN
            val credit = mockedCredit.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Success(person)

            whenever(personRepository.getPerson(credit.id)).thenReturn(dataResult)

            //WHEN

            val result = getPersonInformationUseCase.invoke(credit.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is person invoke fail`(){
        runBlocking {

            //GIVEN
            val credit = mockedCredit.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(personRepository.getPerson(credit.id)).thenReturn(dataResult)

            //WHEN

            val result = getPersonInformationUseCase.invoke(credit.id)

            //THEN
            Assert.assertEquals(dataResult, result)
        }
    }

}