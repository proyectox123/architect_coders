package com.mho.training.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.user.LogInParams
import com.example.android.usecases.LogInUseCase
import com.mho.training.utils.CredentialsValidator
import com.mho.training.utils.Event

class LogInViewModel(
    private val credentialsValidator: CredentialsValidator,
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _events = MutableLiveData<Event<Navigation>>()
    val events: LiveData<Event<Navigation>> get() = _events

    private val logInSuccessFunction: (String) -> Unit = {
        _events.value = Event(Navigation.NavigateToMain)
    }

    private val logInErrorFunction: () -> Unit = {
        _events.value = Event(Navigation.ShowLogInError)
    }

    fun onLogIn() {
        val areCredentialsValid = credentialsValidator.validateUsername(username.value)
                && credentialsValidator.validatePassword(password.value)
        if(areCredentialsValid){
            logInUseCase.invoke(
                LogInParams(
                    username.value ?: "",
                    password.value ?: ""
                ),
                logInSuccessFunction,
                logInErrorFunction
            )

            return
        }

        logInErrorFunction()
    }

    //region Inner Classes & Interfaces

    sealed class Navigation {
        object NavigateToMain: Navigation()
        object ShowLogInError: Navigation()
    }

    //endregion
}