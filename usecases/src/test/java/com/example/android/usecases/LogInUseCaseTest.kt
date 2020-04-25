package com.example.android.usecases

import com.example.android.data.repositories.LogInRepository
import com.example.android.domain.user.LogInParams
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
class LogInUseCaseTest{

    @Mock lateinit var logInRepository: LogInRepository
    @Mock lateinit var successFunctionMock: (String) -> Unit
    @Mock lateinit var errorFunctionMock: () -> Unit

    private lateinit var logInParams: LogInParams
    private lateinit var logInUseCase: LogInUseCase

    @Before
    fun setUp() {
        logInUseCase = LogInUseCase(logInRepository)
        logInParams = LogInParams("username", "password")
    }

    @Test
    fun `logInUseCase should call repository with given params`() {
        //WHEN
        logInUseCase.invoke(logInParams, successFunctionMock, errorFunctionMock)

        //THEN
        verify(logInRepository).logIn(any(), any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `logInUseCase should call given success function when repository returns successful answer`() {
        //GIVEN
        given(logInRepository.logIn(eq(logInParams), any(), any())).willAnswer {
            (it.arguments[1] as (String) -> Unit).invoke("token")
        }

        //WHEN
        logInUseCase.invoke(logInParams, successFunctionMock, errorFunctionMock)

        //THEN
        verify(successFunctionMock).invoke("token")
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `logInUseCase should call given error function when repository returns failed answer`() {
        //GIVEN
        given(logInRepository.logIn(eq(logInParams), any(), any())).willAnswer {
            (it.arguments[2] as () -> Unit).invoke()
        }

        //WHEN
        logInUseCase.invoke(logInParams, successFunctionMock, errorFunctionMock)

        //THEN
        verify(errorFunctionMock).invoke()
    }
}