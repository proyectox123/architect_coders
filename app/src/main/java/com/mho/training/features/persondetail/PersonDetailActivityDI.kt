package com.mho.training.features.persondetail

import com.example.android.data.repositories.MovieRepository
import com.example.android.data.repositories.PersonRepository
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.example.android.usecases.GetPersonInformationUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class PersonDetailActivityModule(
    private val personId: Int
){

    @Provides
    fun personDetailViewModelProvider(
        getPersonInformationUseCase: GetPersonInformationUseCase,
        getMovieListByPersonUseCase: GetMovieListByPersonUseCase
    ) = PersonDetailViewModel(
        personId,
        getPersonInformationUseCase,
        getMovieListByPersonUseCase
    )

    @Provides
    fun getPersonInformationUseCaseProvider(personRepository: PersonRepository) =
        GetPersonInformationUseCase(personRepository)

    @Provides
    fun getMovieListByPersonUseCaseProvider(movieRepository: MovieRepository) =
        GetMovieListByPersonUseCase(movieRepository)

}

@Subcomponent(modules = [(PersonDetailActivityModule::class)])
interface PersonDetailActivityComponent {
    val personDetailViewModel: PersonDetailViewModel
}