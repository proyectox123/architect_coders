package com.example.android.data.repositories

import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
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
class PersonRepositoryTest {

    @Mock
    lateinit var remotePersonDataSource: RemotePersonDataSource

    private lateinit var personRepository: PersonRepository

    @Before
    fun setUp(){
        personRepository = PersonRepository(remotePersonDataSource)
    }

    @Test
    fun `getPerson from remote data source should return expected success person with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val expectedDataResult = DataResult.Success(person)

            given(remotePersonDataSource.getPerson(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = personRepository.getPerson(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

    @Test
    fun `getPerson from remote data source should return expected error with given movie id`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val expectedDataResult = DataResult.Error(IOException(""))

            given(remotePersonDataSource.getPerson(movie.id)).willReturn(expectedDataResult)

            //WHEN
            val result = personRepository.getPerson(movie.id)

            //THEN
            assertThat(expectedDataResult, `is`(result))
        }
    }

}