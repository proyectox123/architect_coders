package com.example.android.usecases

import com.example.android.data.repositories.LogInRepository
import com.example.android.domain.user.LogInParams

class LogInUseCase(
    private val logInRepository: LogInRepository
) {

    fun invoke(logInParams: LogInParams, success: (String) -> Unit, error: () -> Unit){
        logInRepository.logIn(logInParams, success, error)
    }

}