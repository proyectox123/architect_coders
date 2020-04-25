package com.example.android.data.repositories

import com.example.android.data.sources.RemoteLogInDataSource
import com.example.android.testshared.mockedLogInParams
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogInRepositoryTest{

    @Mock lateinit var successFunctionMock: (String) -> Unit
    @Mock lateinit var errorFunctionMock: () -> Unit
    @Mock lateinit var remoteLogInDataSource: RemoteLogInDataSource

    private lateinit var logInRepository: LogInRepository

    @Before
    fun setUp() {
        logInRepository = LogInRepository(remoteLogInDataSource)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `when user password matches with custom value should call success function`() {
        //GIVEN
        val logInParams = mockedLogInParams.copy(username = "username")
        given(remoteLogInDataSource.logIn(eq(logInParams), any(), any())).willAnswer {
            (it.arguments[1] as (String) -> Unit).invoke("token")
        }

        //WHEN
        logInRepository.logIn(logInParams, successFunctionMock, errorFunctionMock)

        //THEN
        verify(successFunctionMock).invoke("token")
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `when user password doesn't match with custom value should call success function`() {
        //GIVEN
        val logInParams = mockedLogInParams.copy(username = "username")
        given(remoteLogInDataSource.logIn(eq(logInParams), any(), any())).willAnswer {
            (it.arguments[2] as () -> Unit).invoke()
        }

        //WHEN
        logInRepository.logIn(logInParams, successFunctionMock, errorFunctionMock)

        //THEN
        verify(errorFunctionMock).invoke()
    }

}