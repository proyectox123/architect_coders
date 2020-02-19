package com.mho.training.features.credits

import com.example.android.data.repositories.CreditRepository
import com.example.android.usecases.GetCreditListUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class CreditsFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun creditsViewModelProvider(
        getCreditListUseCase: GetCreditListUseCase
    ) = CreditsViewModel(
        movieId,
        getCreditListUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getCreditListUseCaseProvider(creditRepository: CreditRepository) =
        GetCreditListUseCase(creditRepository)

}

@Subcomponent(modules = [(CreditsFragmentModule::class)])
interface CreditsFragmentComponent {
    val creditsViewModel: CreditsViewModel
}