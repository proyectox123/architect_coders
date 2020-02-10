package com.example.android.data.repositories

import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.domain.result.DataResult
import com.example.android.mocks.mockedMovie
import com.example.android.mocks.mockedPerson
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
class PersonRepositoryTest {

    @Mock
    lateinit var remotePersonDataSource: RemotePersonDataSource

    lateinit var personRepository: PersonRepository

    @Before
    fun setUp(){
        personRepository = PersonRepository(remotePersonDataSource)
    }

    @Test
    fun `is getPerson from remote data source success`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)
            val person = mockedPerson.copy(id = 1)

            val dataResult = DataResult.Success(person)

            whenever(remotePersonDataSource.getPerson(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = personRepository.getPerson(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

    @Test
    fun `is getPerson from remote data source fail`(){
        runBlocking {

            //GIVEN
            val movie = mockedMovie.copy(id = 1)

            val dataResult = DataResult.Error(IOException(""))

            whenever(remotePersonDataSource.getPerson(movie.id)).thenReturn(dataResult)

            //WHEN
            val result = personRepository.getPerson(movie.id)

            //THEN
            assertEquals(dataResult, result)
        }
    }

}