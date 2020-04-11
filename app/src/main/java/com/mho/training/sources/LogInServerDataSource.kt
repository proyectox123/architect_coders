package com.mho.training.sources

import com.example.android.data.sources.RemoteLogInDataSource
import com.example.android.domain.user.LogInParams

class LogInServerDataSource: RemoteLogInDataSource {

    //region Public Methods

    override fun logIn(logInParams: LogInParams, success: (String) -> Unit, error: () -> Unit) {
        with(logInParams){
            if(username == "coders" && password == "architect"){
                success("token")
            }else{
                error()
            }
        }
    }

    //end
}