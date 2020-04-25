package com.example.android.data.repositories

import com.example.android.data.sources.RemoteLogInDataSource
import com.example.android.domain.user.LogInParams

class LogInRepository(
    private val remoteLogInDataSource: RemoteLogInDataSource
) {

    //region Public Methods

    fun logIn(logInParams: LogInParams, success: (String) -> Unit, error: () -> Unit) {
        remoteLogInDataSource.logIn(logInParams, success, error)
    }

    //endregion
}