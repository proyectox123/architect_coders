package com.mho.training.features.persondetail

import com.example.android.data.repositories.PersonRepository
import com.example.android.usecases.GetPersonInformationUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class PersonDetailActivityModule(
    private val personId: Int
){

    @Provides
    fun personDetailViewModelProvider(
        getPersonInformationUseCase: GetPersonInformationUseCase
    ) = PersonDetailViewModel(
        personId,
        getPersonInformationUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getPersonInformationUseCaseProvider(personRepository: PersonRepository) =
        GetPersonInformationUseCase(personRepository)

}

@Subcomponent(modules = [(PersonDetailActivityModule::class)])
interface PersonDetailActivityComponent {
    val personDetailViewModel: PersonDetailViewModel
}