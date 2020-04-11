package com.mho.training.features.login

import com.example.android.data.repositories.LogInRepository
import com.example.android.usecases.LogInUseCase
import com.mho.training.utils.CredentialsValidator
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class LogInActivityModule {

    @Provides
    fun logInViewModelProvider(
        credentialsValidator: CredentialsValidator,
        logInUseCase: LogInUseCase
    ) = LogInViewModel(
        credentialsValidator,
        logInUseCase
    )

    @Provides
    fun getCredentialsValidator() = CredentialsValidator()

    @Provides
    fun getLogInUseCase(logInRepository: LogInRepository) =
        LogInUseCase(logInRepository)
}

@Subcomponent(modules = [(LogInActivityModule::class)])
interface LogInActivityComponent {
    val logInViewModel: LogInViewModel
}