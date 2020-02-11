package com.example.android.usecases

import com.example.android.data.repositories.PersonRepository
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedCredit
import com.example.android.mocks.mockedPerson
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
class GetPersonInformationUseCaseTest {

    @Mock
    lateinit var personRepository: PersonRepository

    private lateinit var getPersonInformationUseCase: GetPersonInformationUseCase

    @Before
    fun setUp(){
        getPersonInformationUseCase = GetPersonInformationUseCase(personRepository)
    }

    @Test
    fun `getPersonInformationUseCase should return expected success person with given credit id`(){
        runBlocking {

            //GIVEN
            val credit = mockedCredit.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Success(person)

            given(personRepository.getPerson(credit.id)).willReturn(expectedDataResult)

            //WHEN

            val result = getPersonInformationUseCase.invoke(credit.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getPersonInformationUseCase should return expected error with given credit id`(){
        runBlocking {

            //GIVEN
            val credit = mockedCredit.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(personRepository.getPerson(credit.id)).willReturn(expectedDataResult)

            //WHEN

            val result = getPersonInformationUseCase.invoke(credit.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}