package com.example.android.data.sources

import com.example.android.domain.user.LogInParams

interface RemoteLogInDataSource {
    fun logIn(logInParams: LogInParams, success: (String) -> Unit, error: () -> Unit)
}