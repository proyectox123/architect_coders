package com.mho.training.features.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.usecases.LogInUseCase
import com.mho.training.utils.CredentialsValidator
import com.mho.training.utils.Event
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogInViewModelTest {

    @Mock private lateinit var logInUseCase: LogInUseCase
    @Mock private lateinit var credentialsValidator: CredentialsValidator

    @Mock lateinit var eventsObserver: Observer<Event<LogInViewModel.Navigation>>

    private lateinit var logInViewModel: LogInViewModel

    private var username: String? = null
    private var password: String? = null

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        logInViewModel = LogInViewModel(
            credentialsValidator,
            logInUseCase
        )

        username = logInViewModel.username.value
        password = logInViewModel.password.value
    }

    @Test
    fun `username should be null when view model is initialized`() {
        //THEN
        assertNull(username)
    }

    @Test
    fun `password should be null when view model is initialized`() {
        //THEN
        assertNull(password)
    }

    @Test
    fun `onLogIn should call validate username from credentials validator`(){
        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(credentialsValidator).validateUsername(username)
    }

    @Test
    fun `onLogIn should call validate password from credentials validator if validate username is true`(){
        //GIVEN
        given(credentialsValidator.validateUsername(username)).willReturn(true)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(credentialsValidator).validatePassword(password)
    }

    @Test
    fun `onLogIn should call log in use case when credentials validator returns true`() {
        //GIVEN
        given(credentialsValidator.validateUsername(username)).willReturn(true)
        given(credentialsValidator.validatePassword(password)).willReturn(true)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(logInUseCase).invoke(any(), any(), any())
    }

    @Test
    fun `onLogIn should not call log in use case when validate username from credentials validator returns false`() {
        //GIVEN
        given(credentialsValidator.validateUsername(username)).willReturn(false)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(logInUseCase, times(0)).invoke(any(), any(), any())
    }

    @Test
    fun `onLogIn should not call log in use case when validate password from credentials validator returns false`() {
        //GIVEN
        given(credentialsValidator.validateUsername(username)).willReturn(true)
        given(credentialsValidator.validatePassword(password)).willReturn(false)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(logInUseCase, times(0)).invoke(any(), any(), any())
    }

    @Test
    fun `onLogIn should show log in error when validate username from credentials validator returns false`() {
        //GIVEN
        logInViewModel.events.observeForever(eventsObserver)

        given(credentialsValidator.validateUsername(username)).willReturn(false)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(eventsObserver).onChanged(Event(LogInViewModel.Navigation.ShowLogInError))
    }

    @Test
    fun `onLogIn should show log in error when validate password from credentials validator returns false`() {
        //GIVEN
        logInViewModel.events.observeForever(eventsObserver)

        given(credentialsValidator.validateUsername(username)).willReturn(true)
        given(credentialsValidator.validatePassword(password)).willReturn(false)

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(eventsObserver).onChanged(Event(LogInViewModel.Navigation.ShowLogInError))
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `onLogIn should launch navigate to main activity when log in use case is success`() {
        //GIVEN
        logInViewModel.events.observeForever(eventsObserver)

        given(credentialsValidator.validateUsername(username)).willReturn(true)
        given(credentialsValidator.validatePassword(password)).willReturn(true)
        given(logInUseCase.invoke(any(), any(), any())).willAnswer {
            (it.arguments[1] as (String) -> Unit).invoke("token")
        }

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(eventsObserver).onChanged(Event(LogInViewModel.Navigation.NavigateToMain))
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `onLogIn should show log in error when log in use case is failed`() {
        //GIVEN
        logInViewModel.events.observeForever(eventsObserver)

        given(credentialsValidator.validateUsername(username)).willReturn(true)
        given(credentialsValidator.validatePassword(password)).willReturn(true)
        given(logInUseCase.invoke(any(), any(), any())).willAnswer {
            (it.arguments[2] as () -> Unit).invoke()
        }

        //WHEN
        logInViewModel.onLogIn()

        //THEN
        verify(eventsObserver).onChanged(Event(LogInViewModel.Navigation.ShowLogInError))
    }
}